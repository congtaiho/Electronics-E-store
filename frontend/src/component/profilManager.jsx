import React, { useState } from 'react'
import { MDBBtn } from 'mdb-react-ui-kit'
import sweetalert from 'sweetalert2'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'

const ProfileManager = ({ onloadStateFromLocalStorage, onSaveStateToLocalStorage }) => {
    const sessionUserOnload = onloadStateFromLocalStorage()
    const [isEditing, setIsEditing] = useState()
    const navigate = useNavigate()
    const [sessionUser, setSessionUser] = useState({
        id: sessionUserOnload.id,
        prenom: sessionUserOnload.prenom,
        nom: sessionUserOnload.nom,
        email: sessionUserOnload.email,
        password: sessionUserOnload.password,
        noCivique: sessionUserOnload.noCivique,
        street: sessionUserOnload.street,
        city: sessionUserOnload.city,
        pays: sessionUserOnload.pays,
        session: sessionUserOnload.session,
        panier: sessionUserOnload.panier,
        imageProfil: sessionUserOnload.imageProfil
    })

    const handleOnSaveProfil = () => {
        axios.put('http://localhost:5000/profil', sessionUser)
            .then(response => {
                console.log('profil manager response', response)
                if (response.status === 200) {
                    setIsEditing(false)
                    navigate('/profil_manager')
                }
            })
            .catch(error => {
                console.error(error)
            })
    }

    const handleEditClick = (event) => {
        event.preventDefault()
        setIsEditing(true)
    }

    const handleCancelClick = (event) => {
        event.preventDefault()
        setIsEditing(false)
        window.location.reload()
    }

    const handleValidateform = (event) => {
        event.preventDefault()

        const formData = new FormData(event.target)
        const formDataObject = {}

        formData.forEach((value, key) => {
            formDataObject[key] = value
        })

        if (formDataObject.password === '' || formDataObject.confirmPassword === '' || formDataObject.confirmPassword !== formDataObject.password) {
            sweetalert.fire({
                title: 'Password non conforme'
            })
        }

        handleOnSaveProfil()
        onSaveStateToLocalStorage(sessionUser)
    }

    return (
        <>

            <div className='register-container'>
                <h1>Create an Account</h1>

                <form onSubmit={handleValidateform} className='register-form' method='post'>
                    <input type='hidden' id='id' name='id' value={sessionUser.id} />
                    <label htmlFor='prenom'>Prénom</label>
                    <input
                        type='text'
                        id='prenom'
                        name='prenom'
                        value={isEditing ? (sessionUser.prenom ? sessionUser.prenom : null) : (sessionUser.prenom ? sessionUser.prenom : '')}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, prenom: e.target.value }) : null}
                    />

                    <label htmlFor='nom'>Nom</label>
                    <input
                        type='text'
                        id='nom'
                        name='nom'
                        value={isEditing ? (sessionUser.nom ? sessionUser.nom : null) : (sessionUser.nom ? sessionUser.nom : '')}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, nom: e.target.value }) : null}
                    />

                    <label htmlFor='email'>Email</label>
                    <input
                        type='email'
                        id='email'
                        name='email'
                        value={isEditing ? (sessionUser.email ? sessionUser.email : null) : (sessionUser.email ? sessionUser.email : '')}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, email: e.target.value }) : null}
                    />

                    <label htmlFor='password'>Change Password</label>
                    <input
                        type='password'
                        id='password'
                        name='password'
                        value={isEditing ? null : ''}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, password: e.target.value }) : null}
                    />

                    <label htmlFor='confirmPassword'>Confirm Password</label>
                    <input
                        type='password'
                        id='confirmPassword'
                        name='confirmPassword'
                        value={isEditing ? null : ''}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, password: e.target.value }) : null}
                    />

                    <label htmlFor='nocivique'>No civique</label>
                    <input
                        type='text'
                        id='nocivique'
                        name='nocivique'
                        value={isEditing ? (sessionUser.noCivique ? sessionUser.noCivique : null) : (sessionUser.noCivique ? sessionUser.noCivique : '')}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, noCivique: e.target.value }) : null}
                    />

                    <label htmlFor='street'>Street</label>
                    <input
                        type='text'
                        id='street'
                        name='street'
                        value={isEditing ? (sessionUser.street ? sessionUser.street : null) : (sessionUser.street ? sessionUser.street : '')}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, street: e.target.value }) : null}
                    />

                    <label htmlFor='city'>City</label>
                    <input
                        type='text'
                        id='city'
                        name='city'
                        value={isEditing ? (sessionUser.city ? sessionUser.city : null) : (sessionUser.city ? sessionUser.city : '')}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, city: e.target.value }) : null}
                    />

                    <label htmlFor='pays'>Pays</label>
                    <input
                        type='text'
                        id='pays'
                        name='pays'
                        value={isEditing ? (sessionUser.pays ? sessionUser.pays : null) : (sessionUser.pays ? sessionUser.pays : '')}
                        onChange={isEditing ? (e) => setSessionUser({ ...sessionUser, pays: e.target.value }) : null}
                    />
                    <div className='mb-3'>
                        {isEditing
                            ? (
                                <>
                                    <button className='btn btn-primary shadow-0' type='submit' color='primary'>
                                        Enregistrer les modifications
                                    </button>

                                    <button className='btn btn-warning shadow-0' type='submit' onClick={handleCancelClick}>
                                        Annuler
                                    </button>

                                </>
                            )
                            : (
                                <MDBBtn type='button' color='danger' onClick={handleEditClick}>
                                    Modifier
                                </MDBBtn>

                            )}
                    </div>
                </form>

            </div>

        </>
    )
}

export default ProfileManager
