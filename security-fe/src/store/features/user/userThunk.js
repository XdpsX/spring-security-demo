import api from "../../../utils/api"
import { createAsyncThunk } from '@reduxjs/toolkit'
import { getErrorMsg } from '../../../utils/helper'

export const fetchUserProfile = createAsyncThunk(
  'user/fetchUserProfile',
  async (_, { rejectWithValue, fulfillWithValue }) => {
    try {
      const response = await api.get("/users/me")
      return fulfillWithValue(response.data)
    } catch (error) {
      return rejectWithValue(getErrorMsg(error))
    }
  }
)