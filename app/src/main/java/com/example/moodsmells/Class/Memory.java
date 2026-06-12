package com.example.moodsmells.Class;

import android.os.Parcel;
import android.os.Parcelable;

public class Memory implements Parcelable {
        private String id;
        private String smellName;
        private String smellIntensity;
        private String memoryType;
        private String phone;
        private String smellColor;
        private String memoryId;
        private String smellSource;
        private String memoryDate;
        private String smellCategory;
        private String memoryDescription;
        private String memoryLocation;
        private String smellStrength;
        private String smellStyle;
        private String feeling;
        private String photo;

    public Memory() {
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

    protected Memory(Parcel in) {
        id = in.readString();
        smellName = in.readString();
        smellIntensity = in.readString();
        memoryType = in.readString();
        phone = in.readString();
        smellColor = in.readString();
        memoryId = in.readString();
        smellSource = in.readString();
        memoryDate = in.readString();
        smellCategory = in.readString();
        memoryDescription = in.readString();
        memoryLocation = in.readString();
        smellStrength = in.readString();
        smellStyle = in.readString();
        feeling = in.readString();
        photo = in.readString();
    }

    public static final Creator<Memory> CREATOR = new Creator<Memory>() {
        @Override
        public Memory createFromParcel(Parcel in) {
            return new Memory(in);
        }

        @Override
        public Memory[] newArray(int size) {
            return new Memory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(smellName);
        dest.writeString(smellIntensity);
        dest.writeString(memoryType);
        dest.writeString(phone);
        dest.writeString(smellColor);
        dest.writeString(memoryId);
        dest.writeString(smellSource);
        dest.writeString(memoryDate);
        dest.writeString(smellCategory);
        dest.writeString(memoryDescription);
        dest.writeString(memoryLocation);
        dest.writeString(smellStrength);
        dest.writeString(smellStyle);
        dest.writeString(feeling);
        dest.writeString(photo);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSmellName() { return smellName; }
    public void setSmellName(String smellName) { this.smellName = smellName; }

    public String getSmellIntensity() { return smellIntensity; }
    public void setSmellIntensity(String smellIntensity) { this.smellIntensity = smellIntensity; }

    public String getMemoryType() { return memoryType; }
    public void setMemoryType(String memoryType) { this.memoryType = memoryType; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSmellColor() { return smellColor; }
    public void setSmellColor(String smellColor) { this.smellColor = smellColor; }

    public String getMemoryId() { return memoryId; }
    public void setMemoryId(String memoryId) { this.memoryId = memoryId; }

    public String getSmellSource() { return smellSource; }
    public void setSmellSource(String smellSource) { this.smellSource = smellSource; }

    public String getMemoryDate() { return memoryDate; }
    public void setMemoryDate(String memoryDate) { this.memoryDate = memoryDate; }

    public String getSmellCategory() { return smellCategory; }
    public void setSmellCategory(String smellCategory) { this.smellCategory = smellCategory; }

    public String getMemoryDescription() { return memoryDescription; }
    public void setMemoryDescription(String memoryDescription) { this.memoryDescription = memoryDescription; }

    public String getMemoryLocation() { return memoryLocation; }
    public void setMemoryLocation(String memoryLocation) { this.memoryLocation = memoryLocation; }

    public String getSmellStrength() { return smellStrength; }
    public void setSmellStrength(String smellStrength) { this.smellStrength = smellStrength; }

    public String getSmellStyle() { return smellStyle; }
    public void setSmellStyle(String smellStyle) { this.smellStyle = smellStyle; }

    public String getFeeling() { return feeling; }
    public void setFeeling(String feeling) { this.feeling = feeling; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    @Override
    public String toString() {
        return "Memory{" +
                "id='" + id + '\'' +
                ", smellName='" + smellName + '\'' +
                ", smellIntensity='" + smellIntensity + '\'' +
                '}';
    }
}
