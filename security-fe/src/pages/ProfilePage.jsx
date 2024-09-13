import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchUserProfile } from '../store/features/user/userThunk'

function ProfilePage() {
  const dispatch = useDispatch()
  const {
    userProfile,
    loading: { fetchUserProfile: isLoading },
  } = useSelector((state) => state.user)

  useEffect(() => {
    dispatch(fetchUserProfile())
  }, [dispatch])
  if (isLoading) {
    return <div>Loading...</div>
  }
  if (!userProfile) return
  return (
    <div>
      <p>Name: {userProfile.name}</p>
      <p>Email: {userProfile.email}</p>
    </div>
  )
}
export default ProfilePage
