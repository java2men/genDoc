package ru.f13.ikt.logic;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс описывает систему документооборота
 */
public class SystemWorkflow {

    private List<Document> documentRepository;
    private Settings settings;

    /**
     * Конструктор объекта системы документооборота
     */
    public SystemWorkflow() {

        this.documentRepository = new ArrayList<>(0);
        setSettings(Settings.createDefaultSettings());

    }

    /**
     * Удалить документ из системы
     * @param document удаляемый документ
     */
    public void removeDocument(Document document) {

        if (!document.isPartiallySigned()) {
            documentRepository.remove(document);
        }

    }

    /**
     * Подписать документ
     * @param document подписываемый документ
     * @param company компания, которая подписывает документ
     * @return true - документ подписан, false - документ не подписан
     */
    public boolean signingDocument(Document document, Company company) {

        if (validateLimitedTime(document)) {
            return false;
        }

        if (!documentRepository.contains(document)) {
            return false;
        }

        if (document.isSigning()) {
            return false;
        }

        document.sign(company);

        return document.isSigning(company);
    }

    /**
     * Добавить документ в систему
     * @param document добавляемый документ
     * @return true - документ успешно добавлен, false - документ не добавлен
     */
    public boolean addDocument1(Document document) {

        if (document == null) {
            return false;
        }

        Company company1 = document.getCompany1();
        Company company2 = document.getCompany2();

        if (validateCompanyWorkflowLimit(company1) || validateCompanyWorkflowLimit(company2)) {
            return false;
        }

        if (validateCompanyCreateDocumentLimit(company1)) {
            return false;
        }

        if (validateWorkflowLimit(company1, company2)) {
            return false;
        }

        documentRepository.add(document);

        if (signingDocument(document, company1)) {
            company1.transferDocument(document, company2);
            signingDocument(document, company2);
        }

        return document.isSigning();
    }

    /**
     * Подвердить ограниченное время
     * @param document проверяемый документ
     * @return true - ограниченное время подвердилось, false - ограниченное время не подтвердилось
     */
    private boolean validateLimitedTime(Document document) {

        if (document == null || !settings.isLimitedTime()) {
            return false;
        }

        LocalTime start = LocalTime.parse(settings.getStartLimitedTimeValue(), DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime end = LocalTime.parse(settings.getEndLimitedTimeValue(), DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime docTime = document.getDate().toLocalTime();

//        LocalTime start = LocalTime.parse("21:00", DateTimeFormatter.ISO_LOCAL_TIME);
//        LocalTime end = LocalTime.parse("00:00", DateTimeFormatter.ISO_LOCAL_TIME);
//        LocalTime docTime = LocalTime.parse("00:35", DateTimeFormatter.ISO_LOCAL_TIME);

        long b = ChronoUnit.MINUTES.between(start, end);
        long b1 = ChronoUnit.MINUTES.between(start, docTime);
        long b2 = ChronoUnit.MINUTES.between(docTime, end);

        boolean valid = false;
        if (b > 0L) {
            valid = b1 > 0L && b2 > 0L;
        } else if (b < 0) {
            valid = b1 < 0L ^ b2 < 0L;
        } else if (b == 0L) {
            valid = b1 == 0L && b2 == 0L;
        }

        return valid;

    }

    /**
     * Подтвердить ограничение компании на участие в документообороте
     * @param company проверяемая компания
     * @return true - ограничение есть, false - ограничение отсутсвует
     */
    private boolean validateCompanyWorkflowLimit(Company company) {

        if (!settings.isCompanyWorkflowLimit()) {
            return false;
        }

        List<Document> documents = documentRepository.
                stream().
                filter(document -> !document.isSigning() && document.isContainsCompany(company)).
                collect(Collectors.toList());

        int count = documents.size();

        return count >= settings.getCompanyWorkflowLimitValue();
    }

    /**
     * Подвердить ограничение на создание компанией документов
     * @param company проверяемая компания
     * @return true - ограничение есть, false - ограничение отсутсвует
     */
    private boolean validateCompanyCreateDocumentLimit(Company company) {

        if (company == null || !settings.isCompanyCreateDocumentLimit()) {
            return false;
        }

        int count = 0;
        List<Document> companyDocuments = company.getDocuments();

        if (companyDocuments != null) {

            LocalDateTime now = LocalDateTime.now();
            long minutesLimit = settings.getCompanyCreateDocumentLimitedTimeValue() * 60L;
            List<Document> documents = companyDocuments.
                    stream().
                    filter(document -> ChronoUnit.MINUTES.between(now, document.getDate()) <= minutesLimit).
                    collect(Collectors.toList());
            count = documents.size();

        }


        return count >= settings.getCompanyCreateDocumentLimitValue();

    }

    /**
     * Подтвердить ограничение на ведение документооборота менжду 2 компаниями
     * @param company1 первая проверяемая компания
     * @param company2 вторая проверяемая компания
     * @return true - ограничение есть, false - ограничение отсутсвует
     */
    private boolean validateWorkflowLimit(Company company1, Company company2) {

        if (!settings.isWorkflowLimit()) {
            return false;
        }

        int count = 0;
        if (documentRepository != null) {
            List<Document> documents = documentRepository.
                    stream().
                    filter(document -> !document.isSigning() && document.isContainsCompany(company1) && document.isContainsCompany(company2)).
                    collect(Collectors.toList());
            count = documents.size();
        }

        return count >= settings.getWorkflowLimitValue();
    }

    /**
     * Получить настройки ограничений
     * @return объект настроек {@link Settings}
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Установить настройки ограничений
     * @param settings объект настроек {@link Settings}
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

}
