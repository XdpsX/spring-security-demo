import { useEffect } from 'react'

const GoogleSignIn = () => {
  useEffect(() => {
    const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID

    window.onload = function () {
      window.google.accounts.id.initialize({
        client_id: GOOGLE_CLIENT_ID,
        callback: handleCredentialResponse,
      })
      window.google.accounts.id.renderButton(
        document.getElementById('googleBtn'),
        {
          theme: 'outline',
          size: 'medium',
          auto_prompt: false,
          // locale: 'vi',
        }
      )
      // window.google.accounts.id.prompt() // display the One Tap dialog
    }
  }, [])

  const handleCredentialResponse = (response) => {
    console.log('Encoded JWT ID token: ' + response.credential)
  }

  return <div id="googleBtn"></div>
}

export default GoogleSignIn
