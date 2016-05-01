# Search Places
1. What error conditions will you encounter? How should these be handled?
    - When GooglePlacesApi is not connected. This is handled by showing a Toast message to the User 
    - When there are no autocomplete results shown to the user. In this case user cannot move forward to the Place details page.
    - TIMEOUT in getting results from API call. Handled by showing Toast message to the User.
2. Where could the user experience break? How will you prevent this?
    - User exeperience can break when the user tries to rotate the screen while searching for a place. Usually after rotation new activity will be created and all the autocomplete results will disappear. This situation is handled by using android:configChanges="orientation|keyboardHidden|screenSize". As the UI in this application doesn't effect after screen rotation, this configchanges is used.
3. What other improvements can be made?
    - Along with searching for a place, place picker widget can be added to imrpove user experience and show the place details when user clicks on the map.
    - In Place details activity, Map fragment with Latlang bounds of selected place can be added. 