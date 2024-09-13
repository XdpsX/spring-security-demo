import { jwtDecode } from "jwt-decode"

export const getErrorMsg = (error) => {
  const errorDTO = error.response.data
  return errorDTO?.message || 'Internal Server Error'
}

export const getRoleFromToken = (token) => {
  if (token) {
    const decodeToken = jwtDecode(token)
    const expireTime = new Date(decodeToken.exp * 1000)
    if (new Date() > expireTime) {
      localStorage.removeItem('accessToken')
      return ''
    } else {
      return decodeToken.scope
    }
  } else {
    return ''
  }
}