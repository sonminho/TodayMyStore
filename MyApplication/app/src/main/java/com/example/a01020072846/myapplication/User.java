package com.example.a01020072846.myapplication;


public class User {
    private String mId;
    private String mPw;
    private String mName;
    private String mEmail;
    private String mPhone;

    public User() {

    }
    public User(String mId, String mPw, String mName, String mEmail, String mPhone) {
        this.mId = mId;
        this.mPw = mPw;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
    }

    public String getmId() {
        return mId;
    }
    public void setmId(String mId) {
        this.mId = mId;
    }
    public String getmPw() {
        return mPw;
    }
    public void setmPw(String mPw) {
        this.mPw = mPw;
    }
    public String getmName() {
        return mName;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }
    public String getmEmail() {
        return mEmail;
    }
    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
    public String getmPhone() {
        return mPhone;
    }
    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
    @Override
    public String toString() {
        return "User [mId=" + mId + ", mPw=" + mPw + ", mName=" + mName + ", mEmail=" + mEmail + ", mPhone=" + mPhone
                + "]";
    }

}
