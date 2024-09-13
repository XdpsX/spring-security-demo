import api from "../../../utils/api"
import { createAsyncThunk } from '@reduxjs/toolkit'
import { getErrorMsg } from '../../../utils/helper'

export const registerThunk = createAsyncThunk(
  'auth/registerThunk',
  async (registerRequest, { rejectWithValue, fulfillWithValue }) => {
    try {
      const response = await api.post("/auth/register", registerRequest)
      return fulfillWithValue(response.data)
    } catch (error) {
      return rejectWithValue(getErrorMsg(error))
    }
  }
)
export const loginThunk = createAsyncThunk(
  'auth/loginThunk',
  async (loginRequest, { rejectWithValue, fulfillWithValue }) => {
    try {
      const response = await api.post("/auth/login", loginRequest)
      return fulfillWithValue(response.data)
    } catch (error) {
      return rejectWithValue(getErrorMsg(error))
    }
  }
)