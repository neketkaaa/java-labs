package lab2.registration.model;

/**
 * Класс для информации о преподавателе
 */
public class Instructor extends Person {

    /**
     * Идентификаторы курсов, которые может вести преподаватель
     */
    long[] canTeach;

    // TODO: добавить геттеры и сеттеры

    public long[] getCanTeach() {
        return canTeach;
    }

    public void setCanTeach(long[] canTeach) {
        this.canTeach = canTeach;
    }
    
}
