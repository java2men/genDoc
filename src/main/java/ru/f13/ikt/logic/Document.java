package ru.f13.ikt.logic;

import java.time.LocalDateTime;


public class Document {

    private String name;
    private Company company1;
    private Company company2;
    private boolean signing1 = false;
    private boolean signing2 = false;
    private LocalDateTime date;

    /**
     * Конструктор для объекта документа
     * @param company1 компания указанная в 1 стороне (создающая документ)
     * @param company2 компания указанная во 2 стороне
     */
    public Document(Company company1, Company company2) {
        this.name = generateName(company1, company2);
        this.company1 = company1;
        this.company2 = company2;
        this.date = LocalDateTime.now();
    }

    /**
     * Получить имя документа
     * @return имя документа
     */
    public String getName() {
        return name;
    }

    /**
     * Генерировать имя документа на основе компаний
     * @param company1 первая компания
     * @param company2 вторая компания
     * @return сгенерированное имя
     */
    private String generateName(Company company1, Company company2) {

        if (company1 == null || company2 == null) {
            return null;
        }

        return "Doc".concat(String.valueOf(company1.hashCode())).concat("&").concat(String.valueOf(company2.hashCode()));
    }

    /**
     * Получить компанию 1 стороны (создавшей документ)
     * @return компания 1 стороны
     */
    public Company getCompany1() {
        return company1;
    }

    /**
     * Установить компанию 1 стороны (создавшей документ)
     * @param company1 компания 1 стороны
     */
    public void setCompany1(Company company1) {
        this.company1 = company1;
    }

    /**
     * Получить компанию 2 стороны
     * @return компания 2 стороны
     */
    public Company getCompany2() {
        return company2;
    }

    /**
     * Установить компанию 2 стороны
     * @param company2 компания 2 стороны
     */
    public void setCompany2(Company company2) {
        this.company2 = company2;
    }

    /**
     * Содержится ли компания в документе
     * @param company проверяемая компания
     * @return true - компания содержится, false - компания не содержится
     */
    public boolean isContainsCompany(Company company) {

        if (company == null || company1 == null || company1 == null) {
            return false;
        }


        return company.equals(company1) || company.equals(company2);
    }

    /**
     * Подписать документ компанией
     * @param company компания, которая подписывает документ
     */
    public void sign(Company company) {

        if (!company.isContainsDocument(this)) {
            return;
        }

        if (company instanceof Company1) {
            signing1 = true;
        } else if (company instanceof Company2) {
            signing2 = true;
        }

    }

    /**
     * Проверить подписан ли документ компанией
     * @param company компания, которая предположительно подписала
     * @return true - подписала, false - не подписана
     */
    public boolean isSigning(Company company) {
        boolean result = false;
        if (company instanceof Company1) {
            result = signing1;
        } else if (company instanceof Company2) {
            result = signing2;
        }

        return result;
    }

    /**
     * Проверить подписан ли документы обеими сторонами
     * @return true - подписан, false - не подписан
     */
    public boolean isSigning() {
        return isSigning(company1) && isSigning(company2);
    }

    /**
     * Проверить подписан ли документ частично
     * @return true - частично подписан, false - частично не подписан
     */
    public boolean isPartiallySigned() {
        return signing1 ^ signing2;
    }

    /**
     * Сбросить подписания
     */
    public void resetSigning() {
        signing1 = false;
        signing2 = false;
    }

    /**
     * Получить дату создания документа
     * @return объект {@link LocalDateTime}
     */
    public LocalDateTime getDate() {
        return date;
    }

}
