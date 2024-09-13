import { useSelector } from 'react-redux'
import { Navigate } from 'react-router-dom'
import { getRoleFromToken } from '../utils/helper'

// eslint-disable-next-line react/prop-types
function ProtectedRoute({ children, role }) {
  const { accessToken } = useSelector((state) => state.auth)

  if (!accessToken) {
    return <Navigate to="/" />
  }

  const userRole = getRoleFromToken(accessToken)
  if (role && userRole !== role) {
    return <Navigate to="/access-denied" />
  }

  return children
}
export default ProtectedRoute
