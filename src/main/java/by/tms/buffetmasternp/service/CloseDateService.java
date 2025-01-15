package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.entity.CloseDate;
import by.tms.buffetmasternp.repository.CloseDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CloseDateService {

    @Autowired
    private CloseDateRepository closeDateRepository;

    public List<String> getAllCloseDates() {

        List<CloseDate> closeDates = closeDateRepository.findAll();
        List<LocalDate> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        List<String> formattedDates = new ArrayList<>();

        for (CloseDate closeDate : closeDates) {
            dates.add(closeDate.getDate());
        }

        for (LocalDate date : dates) {
            formattedDates.add("'" + formatter.format(date) + "'");
        }

        return formattedDates;
    }

    public void addCloseDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH);
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(stringDate, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException(e.getMessage(), stringDate, 0);
        }
        CloseDate closeDate = new CloseDate();
        closeDate.setDate(localDate);
        closeDateRepository.save(closeDate);
    }

}
