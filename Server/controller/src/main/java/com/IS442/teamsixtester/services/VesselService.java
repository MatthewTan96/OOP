package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.dao.Vessel.VesselDAO;
import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.IS442.teamsixtester.model.Vessel.VesselListDTO;
import com.IS442.teamsixtester.model.VesselTracker.VesselTracker;
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
import java.util.List;
import java.util.Set;

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

    public void bulkUpdate(VesselListDTO vesselListDTO) throws MessagingException {
        VesselTracker vesselTracker = new VesselTracker();

        for (VesselDTO vesselDTO : vesselListDTO.getListVesselDTO()) {
            Vessel checkIfExist1 = getVesselByOutgoing(vesselDTO.getAbbrVslM(), vesselDTO.getOutVoyN());

            if (checkIfExist1 != null) {
                Vessel newVessel = updateVessel(checkIfExist1, vesselDTO);
                Set<Account> AccountsSubscribed = newVessel.getSubscribedByAccounts();
                for (Account account : AccountsSubscribed) {
                    vesselTracker.addVessel(account.getEmail(), newVessel);
                }
            } else {
                addVessel(vesselDTO.toTrueClass());
            }
        }

        //check departure time too!! //check if got changes then add to vesseltracker pls.

        //email
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
                    "<h3> Dear user, please note that there is the changes to the berthing time for the following vessels you have subscribed to.</h3> \n" +
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Vessel Short Name</th>\n" +
                    "    <th>Previous Berthing Time</th>\n" +
                    "    <th>New Berthing Time</th>\n" +
                    "  </tr>";

            // get all vessels subscribed
            for (Vessel s : vesselTracker.getUserAndSubscribedVessels().get(email)) {
                mailContent += "<tr>\n" +
                        "    <td>" + s.getAbbrVslM() + "</td>\n" +
                        "    <td>" + s.getFirstBerthTime() +
                        "    <td>" + s.getBthgDt() +
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

    public Vessel updateVessel(Vessel existingVessel, VesselDTO toChangeVessel) {
        LocalDateTime firstBerthTime = LocalDateTime.parse(existingVessel.getFirstBerthTime());
        LocalDateTime changedBerthTime = LocalDateTime.parse(toChangeVessel.getBthgDt());
        LocalDateTime currentBerthTime = LocalDateTime.parse(existingVessel.getBthgDt());
        Duration duration = Duration.between(firstBerthTime, changedBerthTime);

        if (!(changedBerthTime.isEqual(currentBerthTime))) {
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