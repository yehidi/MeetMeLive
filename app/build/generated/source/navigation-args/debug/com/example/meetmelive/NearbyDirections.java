package com.example.meetmelive;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class NearbyDirections {
  private NearbyDirections() {
  }

  @NonNull
  public static ActionNearbyToSendRequestFragment actionNearbyToSendRequestFragment(
      @NonNull String username, @NonNull String userimage) {
    return new ActionNearbyToSendRequestFragment(username, userimage);
  }

  public static class ActionNearbyToSendRequestFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNearbyToSendRequestFragment(@NonNull String username, @NonNull String userimage) {
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
    public ActionNearbyToSendRequestFragment setUsername(@NonNull String username) {
      if (username == null) {
        throw new IllegalArgumentException("Argument \"username\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("username", username);
      return this;
    }

    @NonNull
    public ActionNearbyToSendRequestFragment setUserimage(@NonNull String userimage) {
      if (userimage == null) {
        throw new IllegalArgumentException("Argument \"userimage\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userimage", userimage);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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
    public int getActionId() {
      return R.id.action_Nearby_to_sendRequestFragment;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNearbyToSendRequestFragment that = (ActionNearbyToSendRequestFragment) object;
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
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
      result = 31 * result + (getUserimage() != null ? getUserimage().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNearbyToSendRequestFragment(actionId=" + getActionId() + "){"
          + "username=" + getUsername()
          + ", userimage=" + getUserimage()
          + "}";
    }
  }
}
