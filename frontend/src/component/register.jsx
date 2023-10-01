import React from 'react'
import axios from 'axios'
import sweetalert from 'sweetalert2'
import { useNavigate } from 'react-router'

const Register = () => {
    const navigate = useNavigate()
    const handleSubmit = (event) => {
        event.preventDefault()
        const formData = new FormData(event.target)
        const email = formData.get('email')
        const password = formData.get('password')
        const confirmPassword = formData.get('confirmPassword')

        if (password === confirmPassword) {
            axios.post('http://localhost:5000/register', { email, password })
                .then(response => {
                    if (response.status === 200) {
                        sweetalert.fire({
                            title: 'Création réussie'

                        })
                        navigate('/account')
                    }
                })
                .catch(error => {
                    console.error(error.response.status)
                    if (error.response.status === 409) {
                        sweetalert.fire({
                            title: 'Email déjà utilisé'
                        })
                    }
                })
        } else {
            sweetalert.fire({
                title: 'Les mots de passe ne correspondent pas'
            })
        }
    }

    return (
        <div className='register-container'>
            <h1>Create an Account</h1>
            <form onSubmit={(event) => handleSubmit(event)} className='register-form'>
                <label htmlFor='email'>Email</label>
                <input type='email' id='email' name='email' />

                <label htmlFor='password'>Password</label>
                <input type='password' id='password' name='password' />

                <label htmlFor='confirmPassword'>Confirm Password</label>
                <input type='password' id='confirmPassword' name='confirmPassword' />

                <button type='submit' className='register-button'>Register</button>
            </form>
        </div>
    )
}
export default Register
