package com.example.moodsmells;

import android.os.Parcel;
import android.os.Parcelable;

public class Memory implements Parcelable {
        private String smellName;
        private  String smellIntensity;
        private  String memoryType;
        private String phone;
        private String smellColor;
        private  String memoryId;
        private  String smellSource;
        private  String memoryDate;
        private  String smellCategory;
        private  String memoryDescription;
        private  String memoryLocation;
        private  String smellStrength;
        private  String smellStyle;
        private  String feeling;
        private String photo;

    public Memory() {
        // مطلوب من Firebase
    }
 public Memory(String smellName, String smellIntensity, String memoryType, String phone, String smellColor,
                String memoryId, String smellSource, String memoryDate, String smellCategory, String memoryDescription,
                String memoryLocation, String smellStrength, String smellStyle, String feeling, String photo) {
            this.smellName = smellName;
            this.smellIntensity = smellIntensity;
            this.memoryType = memoryType;
            this.phone = phone;
            this.smellColor = smellColor;
            this.memoryId = memoryId;
            this.smellSource = smellSource;
            this.memoryDate = memoryDate;
            this.smellCategory = smellCategory;
            this.memoryDescription = memoryDescription;
            this.memoryLocation = memoryLocation;
            this.smellStrength = smellStrength;
            this.smellStyle = smellStyle;
            this.feeling = feeling;
            this.photo = photo;
        }

    protected  Memory(Parcel in) {
            this.smellName = in.readString();
            this.smellIntensity = in.readString();
            this.memoryType = in.readString();
            this.phone = in.readString();
            this.smellColor = in.readString();
            this.memoryId = in.readString();
            this.smellSource = in.readString();
            this.memoryDate = in.readString();
            this.smellCategory = in.readString();
            this.memoryDescription = in.readString();
            this.memoryLocation = in.readString();
            this.smellStrength = in.readString();
            this.smellStyle = in.readString();
            this.feeling = in.readString();
            this.photo = in.readString();
        }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.smellName);
            dest.writeString(this.smellIntensity);
            dest.writeString(this.memoryType);
            dest.writeString(this.phone);
            dest.writeString(this.smellColor);
            dest.writeString(this.memoryId);
            dest.writeString(this.smellSource);
            dest.writeString(this.memoryDate);
            dest.writeString(this.smellCategory);
            dest.writeString(this.memoryDescription);
            dest.writeString(this.memoryLocation);
            dest.writeString(this.smellStrength);
            dest.writeString(this.smellStyle);
            dest.writeString(this.feeling);
            dest.writeString(this.photo);
        }

        public String getSmellName() {
            return smellName;
        }

        public void setSmellName(String smellName) {
            this.smellName = smellName;
        }

        public String getSmellIntensity() {
            return smellIntensity;
        }

        public void setSmellIntensity(String smellIntensity) {
            this.smellIntensity = smellIntensity;
        }

        public String getMemoryType() {
            return memoryType;
        }

        public void setMemoryType(String memoryType) {
            this.memoryType = memoryType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSmellColor() {
            return smellColor;
        }

        public void setSmellColor(String smellColor) {
            this.smellColor = smellColor;
        }

        public String getMemoryId() {
            return memoryId;
        }

        public void setMemoryId(String memoryId) {
            this.memoryId = memoryId;
        }

        public String getSmellSource() {
            return smellSource;
        }

        public void setSmellSource(String smellSource) {
            this.smellSource = smellSource;
        }

        public String getMemoryDate() {
            return memoryDate;
        }

        public void setMemoryDate(String memoryDate) {
            this.memoryDate = memoryDate;
        }

        public String getSmellCategory() {
            return smellCategory;
        }

        public void setSmellCategory(String smellCategory) {
            this.smellCategory = smellCategory;
        }

        public String getMemoryDescription() {
            return memoryDescription;
        }

        public void setMemoryDescription(String memoryDescription) {
            this.memoryDescription = memoryDescription;
        }

        public String getMemoryLocation() {
            return memoryLocation;
        }

        public void setMemoryLocation(String memoryLocation) {
            this.memoryLocation = memoryLocation;
        }

        public String getSmellStrength() {
            return smellStrength;
        }

        public void setSmellStrength(String smellStrength) {
            this.smellStrength = smellStrength;
        }

        public String getSmellStyle() {
            return smellStyle;
        }

        public void setSmellStyle(String smellStyle) {
            this.smellStyle = smellStyle;
        }

        public String getFeeling() {
            return feeling;
        }

        public void setFeeling(String feeling) {
            this.feeling = feeling;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        @Override
        public String toString() {
            return "Smell{" +
                    "smellName='" + smellName + '\'' +
                    ", smellIntensity='" + smellIntensity + '\'' +
                    ", memoryType='" + memoryType + '\'' +
                    ", phone='" + phone + '\'' +
                    ", smellColor='" + smellColor + '\'' +
                    ", memoryId='" + memoryId + '\'' +
                    ", smellSource='" + smellSource + '\'' +
                    ", memoryDate='" + memoryDate + '\'' +
                    ", smellCategory='" + smellCategory + '\'' +
                    ", memoryDescription='" + memoryDescription + '\'' +
                    ", memoryLocation='" + memoryLocation + '\'' +
                    ", smellStrength='" + smellStrength + '\'' +
                    ", smellStyle='" + smellStyle + '\'' +
                    ", feeling='" + feeling + '\'' +
                    ", photo='" + photo + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

    }
