package pl.klobut.books_app.GUI.DTO;

public interface BookViewDTO {
    Long getId();

    String getTitle();

    String getSTART_READING();

    Boolean getARE_YOU_FINISH_READING();

    Boolean getYOU_STOPPED_READING();

    String getDESCRIPTION();

    Integer getPOINTS();
}
