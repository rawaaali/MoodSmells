package com.example.moodsmells;

public class SmellsItem {
        private String name;
        private String mood;
        private String year;
        private String type;
        private String photo;

        public SmellsItem() { }

        public SmellsItem(String name, String mood, String year, String type, String photo) {
            this.name = name;
            this.mood = mood;
            this.year = year;
            this.type = type;
            this.photo = photo;
        }

        public String getName() { return name; }
        public String getMood() { return mood; }
        public String getYear() { return year; }
        public String getType() { return type; }
        public String getPhoto() { return photo; }


}
