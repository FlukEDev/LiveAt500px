package com.example.fluke.liveat500px.deo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FLUKE on 2/19/2016 AD.
 */
public class PhotoItemDao implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("tags")
    @Expose
    private List<String> tags = new ArrayList<String>();
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("camera")
    @Expose
    private String camera;
    @SerializedName("lens")
    @Expose
    private Object lens;
    @SerializedName("focal_length")
    @Expose
    private String focalLength;
    @SerializedName("iso")
    @Expose
    private String iso;
    @SerializedName("shutter_speed")
    @Expose
    private String shutterSpeed;
    @SerializedName("aperture")
    @Expose
    private String aperture;

    public PhotoItemDao() {

    }

    protected PhotoItemDao(Parcel in) {
        link = in.readString();
        imageUrl = in.readString();
        caption = in.readString();
        username = in.readString();
        profilePicture = in.readString();
        tags = in.createStringArrayList();
        createdTime = in.readString();
        camera = in.readString();
        focalLength = in.readString();
        iso = in.readString();
        shutterSpeed = in.readString();
        aperture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(link);
        dest.writeString(imageUrl);
        dest.writeString(caption);
        dest.writeString(username);
        dest.writeString(profilePicture);
        dest.writeStringList(tags);
        dest.writeString(createdTime);
        dest.writeString(camera);
        dest.writeString(focalLength);
        dest.writeString(iso);
        dest.writeString(shutterSpeed);
        dest.writeString(aperture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemDao> CREATOR = new Creator<PhotoItemDao>() {
        @Override
        public PhotoItemDao createFromParcel(Parcel in) {
            return new PhotoItemDao(in);
        }

        @Override
        public PhotoItemDao[] newArray(int size) {
            return new PhotoItemDao[size];
        }
    };

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     * The caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The profilePicture
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     *
     * @param profilePicture
     * The profile_picture
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     *
     * @return
     * The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     * The createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     *
     * @param createdTime
     * The created_time
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     *
     * @return
     * The camera
     */
    public String getCamera() {
        return camera;
    }

    /**
     *
     * @param camera
     * The camera
     */
    public void setCamera(String camera) {
        this.camera = camera;
    }

    /**
     *
     * @return
     * The lens
     */
    public Object getLens() {
        return lens;
    }

    /**
     *
     * @param lens
     * The lens
     */
    public void setLens(Object lens) {
        this.lens = lens;
    }

    /**
     *
     * @return
     * The focalLength
     */
    public String getFocalLength() {
        return focalLength;
    }

    /**
     *
     * @param focalLength
     * The focal_length
     */
    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    /**
     *
     * @return
     * The iso
     */
    public String getIso() {
        return iso;
    }

    /**
     *
     * @param iso
     * The iso
     */
    public void setIso(String iso) {
        this.iso = iso;
    }

    /**
     *
     * @return
     * The shutterSpeed
     */
    public String getShutterSpeed() {
        return shutterSpeed;
    }

    /**
     *
     * @param shutterSpeed
     * The shutter_speed
     */
    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    /**
     *
     * @return
     * The aperture
     */
    public String getAperture() {
        return aperture;
    }

    /**
     *
     * @param aperture
     * The aperture
     */
    public void setAperture(String aperture) {
        this.aperture = aperture;
    }


}
