# androidtv-list-sample

AndroidTV list sample - only using BaseGridView from Leanback Lib and custom key handler.

This sample demonstrates, how code for a mobile Android app and an Android TV can be re-used
by not using the whole standard leanback library and instead only BaseGridView component
(TvVerticalRecyclerView and TvHorizontalRecyclerView extend this one) - but also with the
consequence of handling all key events instead of relying directly in the system focus mechanism
of Android.

The main problem we found with the leanback library is, that all functionality is included
in base fragments that perform many specific UI stuff that we do not need or that cause issues
when re-using code for the mobile app.

With this sample we demonstrate how this can be achieved using regular fragments instead.