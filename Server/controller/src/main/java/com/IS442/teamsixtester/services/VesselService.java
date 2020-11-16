package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.dao.Vessel.VesselDAO;
import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.IS442.teamsixtester.model.Vessel.VesselListDTO;
import com.IS442.teamsixtester.model.VesselTracker.VesselTracker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.json.*;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@Service
public class VesselService {

    public final VesselDAO vesselDao;

    @Autowired
    public VesselService(@Qualifier("postgres") VesselDAO vesselDao) {
        this.vesselDao = vesselDao;
    }

    @Autowired
    private JavaMailSender mailSender;

    public Vessel addVessel(Vessel vessel) {
        return vesselDao.insertVessel(vessel);
    }

    @CrossOrigin
    public List<Vessel> getAllVessels() {
        return vesselDao.selectAllVessels();
    }

    public Vessel getVesselByIncoming(String abbrVslM, String inVoyN) {
        return vesselDao.selectVesselByIncoming(abbrVslM, inVoyN);
    }

    public Vessel getVesselByOutgoing(String abbrVslM, String outVoyN) {
        return vesselDao.selectVesselByOutgoing(abbrVslM, outVoyN);
    }

    public Vessel getVesselByShortname(String abbrVslM) {
        return vesselDao.selectVesselByShortname(abbrVslM);
    }

    public Vessel deleteVessel(Vessel vessel) {
        vessel.removeAllFavouritedByAccounts();
        vessel.removeAllSubscribedByAccounts();
        return vesselDao.deleteVessel(vessel);
    }

    public void bulkUpdate(String json) throws MessagingException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        List<VesselDTO> list = new ArrayList<VesselDTO>();

        ObjectMapper objectMapper = new ObjectMapper();
        for(int i = 0 ; i < jsonArray.length(); i++) {
            jsonArray.getJSONObject(i).remove("imoN");
            jsonArray.getJSONObject(i).remove("fullVslM");
            jsonArray.getJSONObject(i).remove("shiftSeqN");
            jsonArray.getJSONObject(i).remove("abbrTerminalM");
            jsonArray.getJSONObject(i).remove("fullOutVoyN");
            jsonArray.getJSONObject(i).remove("fullInVoyN");
            VesselDTO vesselDTO = objectMapper.readValue(jsonArray.getJSONObject(i).toString(), VesselDTO.class);
            list.add(vesselDTO);
        }

        VesselTracker vesselTracker = new VesselTracker();
        VesselListDTO vesselListDTO = new VesselListDTO();
        vesselListDTO.setListVesselDTO(list);

        for (VesselDTO vesselDTO : vesselListDTO.getListVesselDTO()) {
            Vessel checkIfExist1 = getVesselByOutgoing(vesselDTO.getAbbrVslM(), vesselDTO.getOutVoyN());
            //if vessel dont exist in database
            if (checkIfExist1 != null) {
                //if berthing time or departure time change
                if (checkBerthTime(checkIfExist1,vesselDTO) || checkDepartureTime(checkIfExist1,vesselDTO) ){
                    Vessel newVessel = updateVessel(checkIfExist1, vesselDTO);
                    // get all accounts subscribed to the updated vessel
                    Set<Account> AccountsSubscribed = newVessel.getSubscribedByAccounts();
                    //add account to VesselTracker object (multimap)
                    for (Account account : AccountsSubscribed) {
                        vesselTracker.addVessel(account.getEmail(), newVessel);
                    }
                } else {
                    updateVessel(checkIfExist1, vesselDTO);
                }
            } else {
                addVessel(vesselDTO.toTrueClass());
            }
        }


        //email
        if (vesselTracker.getUserAndSubscribedVessels().size() != 0) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            //get the email of the user
            for (String email : vesselTracker.getUserAndSubscribedVessels().keySet()) {
                helper.setTo(email);
                String mailContent = "<html>\n" +
                        "<head>\n" +
                        "<style>\n" +
                        "table {\n" +
                        "  font-family: arial, sans-serif;\n" +
                        "  border-collapse: collapse;\n" +
                        "  width: 100%;\n" +
                        "}\n" +
                        "\n" +
                        "td, th {\n" +
                        "  border: 1px solid #dddddd;\n" +
                        "  text-align: left;\n" +
                        "  padding: 8px;\n" +
                        "}\n" +
                        "\n" +
                        "tr:nth-child(even) {\n" +
                        "  background-color: #77c3ec;\n" +
                        "}\n" +
                        "</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<h2>Vessel Berthing Time Change Notification</h2>\n" +
                        "\n" +
                        "<h3> Dear user, please note that there is changes to the berthing/departure time for the following vessels you have subscribed to.</h3> \n  " +
                        "<h4> Listed below are the details of the new berthing/departure time </h4>" +
                        "<table>\n" +
                        "  <tr>\n" +
                        "    <th>Vessel Short Name</th>\n" +
                        "    <th>Berthing Time</th>\n" +
                        "    <th>Departure Time</th>\n" +
                        "  </tr>";

                // get all vessels subscribed
                for (Vessel s : vesselTracker.getUserAndSubscribedVessels().get(email)) {
                    mailContent += "<tr>\n" +
                            "    <td>" + s.getAbbrVslM() + "</td>\n" +
                            "    <td>" + s.getBthgDt() +
                            "    <td>" + s.getUnbthgDt() +
                            "  </tr>";
                }

                mailContent += "</table>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>\n";

                helper.setText(mailContent, true);
                helper.setSubject("PSA - Vessel Changes Notification");
                mailSender.send(message);
            }
        }
    }

    public boolean checkBerthTime(Vessel existingVessel, VesselDTO toChangeVessel){
        LocalDateTime firstBerthTime = LocalDateTime.parse(existingVessel.getFirstBerthTime());
        LocalDateTime changedBerthTime = LocalDateTime.parse(toChangeVessel.getBthgDt());
        LocalDateTime currentBerthTime = LocalDateTime.parse(existingVessel.getBthgDt());
        if (!(changedBerthTime.isEqual(currentBerthTime))) {
            return true;
        }

        return false;
    }

    public boolean checkDepartureTime(Vessel existingVessel, VesselDTO toChangeVessel){
        LocalDateTime changedDepartureTime = LocalDateTime.parse(toChangeVessel.getUnbthgDt());
        LocalDateTime currentDepartureTime = LocalDateTime.parse(existingVessel.getUnbthgDt());
        if (!(changedDepartureTime.isEqual(currentDepartureTime))) {
            return true;
        }
        return false;
    }

    public Vessel updateVessel(Vessel existingVessel, VesselDTO toChangeVessel) {
        LocalDateTime firstBerthTime = LocalDateTime.parse(existingVessel.getFirstBerthTime());
        LocalDateTime changedBerthTime = LocalDateTime.parse(toChangeVessel.getBthgDt());
        Duration duration = Duration.between(firstBerthTime, changedBerthTime);


        if (checkBerthTime(existingVessel,toChangeVessel)){
            existingVessel.setChangeCount(existingVessel.getChangeCount() + 1);
            double numberOfHours = Math.abs((double) (duration.toHours()));
            existingVessel.setDegreeChange(numberOfHours);
            existingVessel.setBthgDt(toChangeVessel.getBthgDt());
        }


        existingVessel.setAbbrVslM(toChangeVessel.getAbbrVslM());
        existingVessel.setInVoyN(toChangeVessel.getInVoyN());
        existingVessel.setOutVoyN(toChangeVessel.getOutVoyN());
        existingVessel.setUnbthgDt(toChangeVessel.getUnbthgDt());
        existingVessel.setBerthN(toChangeVessel.getBerthN());
        existingVessel.setStatus(toChangeVessel.getStatus());

        return vesselDao.updateVessel(existingVessel);
    }
}