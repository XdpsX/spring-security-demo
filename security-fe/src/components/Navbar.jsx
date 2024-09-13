import { useDispatch, useSelector } from 'react-redux'
import { Link, NavLink } from 'react-router-dom'
import { logout } from '../store/features/auth/authSlice'
import { toast } from 'react-toastify'

const Navbar = () => {
  const dispatch = useDispatch()
  const { accessToken } = useSelector((state) => state.auth)
  const isAuthenticated = !!accessToken

  const logoutHandler = () => {
    dispatch(logout())
    toast.success('Logout successfully')
  }

  return (
    <header className="flex items-center justify-between bg-slate-400 text-white text-lg">
      <div>
        <Link to="/" className="navbar-link">
          Home
        </Link>
      </div>
      <ul>
        {isAuthenticated && (
          <li>
            <NavLink to={'/profile'} className="navbar-link">
              Profile
            </NavLink>
          </li>
        )}
      </ul>

      {!isAuthenticated ? (
        <div className="flex items-center gap-2">
          <Link to="/login" className="navbar-link">
            Login
          </Link>
          <Link to="/register" className="navbar-link">
            Register
          </Link>
        </div>
      ) : (
        <button
          onClick={logoutHandler}
          className="px-5 py-2 hover:bg-yellow-500 hover:text-white"
        >
          Logout
        </button>
      )}
    </header>
  )
}

export default Navbar
