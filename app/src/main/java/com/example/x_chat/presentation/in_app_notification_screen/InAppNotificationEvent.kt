package com.example.x_chat.presentation.in_app_notification_screen


sealed class InAppNotificationEvent {
    data class UpdateUserId(val userId: String) : InAppNotificationEvent()
    data object Fetch : InAppNotificationEvent()
    data object Accept : InAppNotificationEvent()
    data object Clear : InAppNotificationEvent()
}