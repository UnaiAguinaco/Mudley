package net.javaguides.springboot.model;

public class AjustesAvanzados {
  String actualPass, email, newPass, newPassR;

  public AjustesAvanzados(String actualPass, String email, String newPass, String newPassR) {
    this.actualPass = actualPass;
    this.email = email;
    this.newPass = newPass;
    this.newPassR = newPassR;
  }

  public String getActualPass() {
    return actualPass;
  }

  public void setActualPass(String actualPass) {
    this.actualPass = actualPass;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNewPass() {
    return newPass;
  }

  public void setNewPass(String newPass) {
    this.newPass = newPass;
  }

  public String getNewPassR() {
    return newPassR;
  }

  public void setNewPassR(String newPassR) {
    this.newPassR = newPassR;
  }

}