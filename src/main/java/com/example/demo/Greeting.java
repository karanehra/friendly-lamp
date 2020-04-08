package com.example.demo;

public class Greeting {
  private final long ID;
  private final String content;

  public Greeting(long ID, String content) {
    this.ID = ID;
    this.content = content;
  }

  public long getID() {
    return this.ID;
  }

  public String getContent() {
    return this.content;
  }

}