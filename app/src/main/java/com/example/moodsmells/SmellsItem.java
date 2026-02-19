package com.example.moodsmells;

public class SmellsItem {
        private String name;
        private String mood;
        private String year;
        private String type;
        private String photo;
        private String place;
        private String phone;
        private String person;
        private String loction;
        private String color;

        public SmellsItem() { }

        public SmellsItem(String name, String mood, String year, String type, String photo,String place, String phone,String person,String loction,String color) {
            this.name = name;
            this.mood = mood;
            this.year = year;
            this.type = type;
            this.photo = photo;
            this.place=place;
            this.phone=phone;
            this.person=person;
            this.loction=loction;
            this.color=color;
        }

        public String getNameMemory() { return name; }
        public String getMood() { return mood; }
        public String getYear() { return year; }
        public String getType() { return type; }
        public String getPhoto() { return photo; }

    public String getPerson() {return person;}

    public String getLoction() {return loction;}

    public String getColor() { return color ;}

    public String getPlace() {return place;}

    public String getPhone() {return phone;}

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
