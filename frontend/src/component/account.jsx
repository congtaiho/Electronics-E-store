import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios'
import sweetalert from 'sweetalert2'
import Utilisateur from '../../../backend/entities/Utilisateur'
const Account = ({ onloadStateFromLocalStorage, onSaveStateToLocalStorage }) => {
    const navigate = useNavigate()
    let sessionUser = onloadStateFromLocalStorage()

    const handleSubmit = (event) => {
        event.preventDefault()
        const formData = new FormData(event.target)

        const email = formData.get('email')
        const password = formData.get('password')

        axios.post('http://localhost:5000/login', { email, password })
            .then(response => {
                const users = response.data
                const userFound = users.find(user => user.email === email && user.password === password)

                if (userFound) {
                    const utilisateur = new Utilisateur(userFound.id, userFound.nom, userFound.prenom, userFound.email, userFound.password, userFound.no_civique, userFound.street, userFound.city, userFound.pays, userFound.image_profil)
                    sessionUser = utilisateur
                    sessionUser.session = true

                    onSaveStateToLocalStorage(sessionUser)
                    navigate('/home')
                } else {
                    sweetalert.fire({
                        title: 'Identifiant invalide'
                    })
                }
            })
            .catch(error => {
                console.error(error)
            })
    }

    return (

        <div className='account-container'>
            <h1>My Account</h1>

            <form onSubmit={(event) => handleSubmit(event)} className='account-form'>
                <label htmlFor='email'>Email</label>
                <input type='email' id='email' name='email' />

                <label htmlFor='password'>Password</label>
                <input type='password' id='password' name='password' />

                <button type='submit' className='account-button'>Login</button>
            </form>
            <p className='account-switch'>
                Don't have an account?
                <Link className='nav-link' to='/register'>Register</Link>
            </p>
        </div>

    )
}
export default Account
