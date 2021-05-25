package com.example.meetmelive;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class SendRequestFragmentDirections {
  private SendRequestFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionSendRequestFragmentToNotificationFragment2() {
    return new ActionOnlyNavDirections(R.id.action_sendRequestFragment_to_notificationFragment2);
  }
}
