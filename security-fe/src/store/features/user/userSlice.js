import { createSlice } from "@reduxjs/toolkit"
import { fetchUserProfile } from "./userThunk"

const initialState = {
  userProfile: null,
  loading: {
    fetchUserProfile: false
  }
}

export const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
  },
  extraReducers: builder => {
    builder
      .addCase(fetchUserProfile.pending, (state) => {
        state.loading.fetchUserProfile = true
      })
      .addCase(fetchUserProfile.rejected, (state) => {
        state.loading.fetchUserProfile = false;
      })
      .addCase(fetchUserProfile.fulfilled, (state, action) => {
        state.loading.fetchUserProfile = false;
        state.userProfile = action.payload
      })
  }
})

export default userSlice.reducer