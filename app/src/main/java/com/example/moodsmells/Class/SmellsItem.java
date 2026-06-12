package com.example.moodsmells.Class;

import android.os.Parcel;
import android.os.Parcelable;

public class SmellsItem implements Parcelable {
    private String id;
    private String smellName;
    private  String smellIntensity;
    private  String memoryType;
    private String phone;
    private String smellColor;
    private  String memoryNumber;
    private  String smellSource;
    private  String memoryDate;
    private  String smellType;
    private  String memoryDescription;
    private  String memoryPlace;
    private  String smellStrength;
    private  String smellStyle;
    private  String feeling;
    private String photo;

    public SmellsItem() {
    }

    public SmellsItem(String id, String smellName, String smellIntensity, String memoryType, String phone, String smellColor,
                     String memoryNumber, String smellSource, String memoryDate, String smellType, String memoryDescription,
                     String memoryPlace, String smellStrength, String smellStyle, String feeling, String photo) {
        this.id = id;
        this.smellName = smellName;
        this.smellIntensity = smellIntensity;
        this.memoryType = memoryType;
        this.phone = phone;
        this.smellColor = smellColor;
        this.memoryNumber = memoryNumber;
        this.smellSource = smellSource;
        this.memoryDate = memoryDate;
        this.smellType = smellType;
        this.memoryDescription = memoryDescription;
        this.memoryPlace = memoryPlace;
        this.smellStrength = smellStrength;
        this.smellStyle = smellStyle;
        this.feeling = feeling;
        this.photo = photo;
    }

    protected SmellsItem(Parcel in) {
        this.id = in.readString();
        this.smellName = in.readString();
        this.smellIntensity = in.readString();
        this.memoryType = in.readString();
        this.phone = in.readString();
        this.smellColor = in.readString();
        this.memoryNumber = in.readString();
        this.smellSource = in.readString();
        this.memoryDate = in.readString();
        this.smellType = in.readString();
        this.memoryDescription = in.readString();
        this.memoryPlace = in.readString();
        this.smellStrength = in.readString();
        this.smellStyle = in.readString();
        this.feeling = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<SmellsItem> CREATOR = new Creator<SmellsItem>() {
        @Override
        public SmellsItem createFromParcel(Parcel in) {
            return new SmellsItem(in);
        }

        @Override
        public SmellsItem[] newArray(int size) {
            return new SmellsItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.smellName);
        dest.writeString(this.smellIntensity);
        dest.writeString(this.memoryType);
        dest.writeString(this.phone);
        dest.writeString(this.smellColor);
        dest.writeString(this.memoryNumber);
        dest.writeString(this.smellSource);
        dest.writeString(this.memoryDate);
        dest.writeString(this.smellType);
        dest.writeString(this.memoryDescription);
        dest.writeString(this.memoryPlace);
        dest.writeString(this.smellStrength);
        dest.writeString(this.smellStyle);
        dest.writeString(this.feeling);
        dest.writeString(this.photo);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getSmellsName() {
        return smellName;
    }
    public void setSmellsName(String smellName) {
        this.smellName = smellName;
    }
    public String getSmellsIntensity() {
        return smellIntensity;
    }
    public void setSmellsIntensity(String smellIntensity) {
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
        return memoryNumber;
    }
    public void setMemoryId(String memoryNumber) {
        this.memoryNumber = memoryNumber;
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
        return smellType;
    }
    public void setSmellCategory(String smellType) {
        this.smellType = smellType;
    }

    public String getMemoryDescription() {
        return memoryDescription;
    }
    public void setMemoryDescription(String memoryDescription) {
        this.memoryDescription = memoryDescription;
    }

    public String getMemoryLocation() {
        return memoryPlace;
    }
    public void setMemoryLocation(String memoryPlace) {
        this.memoryPlace = memoryPlace;
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
    public int describeContents() {
        return 0;
    }
}
