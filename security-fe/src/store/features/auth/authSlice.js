import { createSlice } from "@reduxjs/toolkit"
import { loginThunk, registerThunk } from "./authThunk"

const initialState = {
  accessToken: localStorage.getItem('accessToken'),
  loading: {
    registerThunk: false,
    loginThunk: false
  }
}

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout: (state) => {
      state.accessToken = null
      localStorage.removeItem('accessToken')
    }
  },
  extraReducers: builder => {
    builder
      .addCase(registerThunk.pending, (state) => {
        state.loading.registerThunk = true
      })
      .addCase(registerThunk.rejected, (state) => {
        state.loading.registerThunk = false;
      })
      .addCase(registerThunk.fulfilled, (state, action) => {
        state.loading.registerThunk = false;
        state.accessToken = action.payload.accessToken
        localStorage.setItem('accessToken', action.payload.accessToken)
      })
      .addCase(loginThunk.pending, (state) => {
        state.loading.loginThunk = true
      })
      .addCase(loginThunk.rejected, (state) => {
        state.loading.loginThunk = false;
      })
      .addCase(loginThunk.fulfilled, (state, action) => {
        state.loading.loginThunk = false;
        state.accessToken = action.payload.accessToken
        localStorage.setItem('accessToken', action.payload.accessToken)
      })
  }
})

export const { logout } = authSlice.actions
export default authSlice.reducer