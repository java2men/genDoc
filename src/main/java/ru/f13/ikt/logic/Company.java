package ru.f13.ikt.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывает компанию
 */
public abstract class Company {

    private List<Document> documents;

    public Company() {
        documents = new ArrayList<>(0);
    }

    /**
     * Создать документ текущей компанией
     * @param otherCompany другая компания (для 2 стороны)
     * @return документ
     */
    public Document createDocument(Company otherCompany) {

        Document document = new Document(this, otherCompany);
        addDocument(document);

        return document;
    }

    /**
     * Изменить документ компании который у нее сейчас в наличии
     * @return измененный документ
     */
    public Document changeDocument(Document document) {

        if (document == null) {
            return null;
        }

        //если текущий объект документа не является компанией создавшей документ
        if (!document.getCompany1().equals(this)) {
            document.setCompany2(document.getCompany1());
            document.setCompany1(this);
            document.resetSigning();

            addDocument(document);
        }

        return document;
    }

    /**
     * Установить документ
     * @param document документ
     */
    public void addDocument(Document document) {
        if (documents != null) {
            documents.add(document);
        }
    }

    /**
     * Удалить документ
     * @param document удаляемый документ
     */
    public void removeDocument(Document document) {
        if (documents != null) {
            documents.remove(document);
        }
    }

    /**
     * Проверить есть ли в наличии документ
     * @param document1 проверяемый документ
     * @return true - документ в наличии, false - документа нет в наличии
     */
    public boolean isContainsDocument(Document document1) {
        if (documents == null || document1 == null) {
            return false;
        }

        return documents.stream().anyMatch(document -> document.equals(document1));

    }

    /**
     * Передать документ компании
     * @param document документ, который требуется передать
     * @param company компания, которой передается документ
     */
    public void transferDocument(Document document, Company company) {
        company.addDocument(document);
        removeDocument(document);
    }

    /**
     * Получить список документов компании
     * @return список документов компании
     */
    public List<Document> getDocuments() {
        return documents;
    }
}
