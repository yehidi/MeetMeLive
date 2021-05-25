package com.example.meetmelive;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SendRequestFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private SendRequestFragmentArgs() {
  }

  private SendRequestFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static SendRequestFragmentArgs fromBundle(@NonNull Bundle bundle) {
    SendRequestFragmentArgs __result = new SendRequestFragmentArgs();
    bundle.setClassLoader(SendRequestFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("username")) {
      String username;
      username = bundle.getString("username");
      if (username == null) {
        throw new IllegalArgumentException("Argument \"username\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("username", username);
    } else {
      throw new IllegalArgumentException("Required argument \"username\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("userimage")) {
      String userimage;
      userimage = bundle.getString("userimage");
      if (userimage == null) {
        throw new IllegalArgumentException("Argument \"userimage\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("userimage", userimage);
    } else {
      throw new IllegalArgumentException("Required argument \"userimage\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUsername() {
    return (String) arguments.get("username");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUserimage() {
    return (String) arguments.get("userimage");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("username")) {
      String username = (String) arguments.get("username");
      __result.putString("username", username);
    }
    if (arguments.containsKey("userimage")) {
      String userimage = (String) arguments.get("userimage");
      __result.putString("userimage", userimage);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    SendRequestFragmentArgs that = (SendRequestFragmentArgs) object;
    if (arguments.containsKey("username") != that.arguments.containsKey("username")) {
      return false;
    }
    if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null) {
      return false;
    }
    if (arguments.containsKey("userimage") != that.arguments.containsKey("userimage")) {
      return false;
    }
    if (getUserimage() != null ? !getUserimage().equals(that.getUserimage()) : that.getUserimage() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
    result = 31 * result + (getUserimage() != null ? getUserimage().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SendRequestFragmentArgs{"
        + "username=" + getUsername()
        + ", userimage=" + getUserimage()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(SendRequestFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull String username, @NonNull String userimage) {
      if (username == null) {
        throw new IllegalArgumentException("Argument \"username\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("username", username);
      if (userimage == null) {
        throw new IllegalArgumentException("Argument \"userimage\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userimage", userimage);
    }

    @NonNull
    public SendRequestFragmentArgs build() {
      SendRequestFragmentArgs result = new SendRequestFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setUsername(@NonNull String username) {
      if (username == null) {
        throw new IllegalArgumentException("Argument \"username\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("username", username);
      return this;
    }

    @NonNull
    public Builder setUserimage(@NonNull String userimage) {
      if (userimage == null) {
        throw new IllegalArgumentException("Argument \"userimage\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userimage", userimage);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUsername() {
      return (String) arguments.get("username");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUserimage() {
      return (String) arguments.get("userimage");
    }
  }
}
