import React, { useState } from 'react'
import '../css/contactForm.css'
import axios from 'axios'
import { useNavigate } from 'react-router'
import sweetalert from 'sweetalert2'

function ContactForm () {
    const navigate = useNavigate()

    const [formData, setFormData] = useState({
        name: '',
        email: '',
        message: ''
    })

    const handleChange = (e) => {
        const { name, value } = e.target
        setFormData({
            ...formData,
            [name]: value
        })
    }

    const isValidEmail = (email) => {
        // You can implement your own email validation logic here
        // For a simple check, you can use a regular expression
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        return emailRegex.test(email)
    }

    const handleSubmit = (e) => {
        e.preventDefault()

        const { name, email, message } = formData

        // Check if any of the fields are empty or email is invalid
        if (!name || !email || !message || !isValidEmail(email)) {
            sweetalert.fire({
                title: 'Invalid Input',
                text: 'Please fill in all fields correctly.',
                icon: 'error'
            })
            return
        }

        const sujet = 'Contact Form Submission'

        axios
            .post('http://localhost:5000/envoyer-email', {
                destinataire: email,
                sujet,
                contenu: `
                Thank you for your message!

                We have received your message and will get back to you as soon as possible.

                Best regards,
                The Sac Teams`

            })
            .then((response) => {
                if (response.status === 200) {
                    // Send a second email to 'TheSacTeamBoutique@outlook.com'
                    axios
                        .post('http://localhost:5000/envoyer-email', {
                            destinataire: 'TheSacTeamBoutique@outlook.com',
                            sujet: 'New message from ' + name,
                            contenu: `
                            You have received a message from ${name}:

                            ${message}

                            Best regards,
                            ${name}`
                        })
                        .then((response) => {
                            if (response.status === 200) {
                                navigate('/contactSupportConfirm')
                            } else {
                                console.error('Erreur lors de l\'envoi de l\'e-mail')
                                sweetalert.fire({
                                    title: 'Erreur lors de l\'envoi de l\'e-mail'
                                })
                            }
                        })
                        .catch((error) => {
                            console.error('Erreur lors de l\'envoi de l\'e-mail', error)
                            sweetalert.fire({
                                title: 'Erreur lors de l\'envoi de l\'e-mail'
                            })
                        })
                } else {
                    console.error('Erreur lors de l\'envoi de l\'e-mail')
                    sweetalert.fire({
                        title: 'Erreur lors de l\'envoi de l\'e-mail'
                    })
                }
            })
            .catch((error) => {
                console.error('Erreur lors de l\'envoi de l\'e-mail', error)
                sweetalert.fire({
                    title: 'Erreur lors de l\'envoi de l\'e-mail'
                })
            })
    }

    return (
        <div className='container-contact-us'>
            <div className='content'>
                <div className='left-side'>
                    <div className='address details'>
                        <i className='fas fa-map-marker-alt' />
                        <div className='topic'>Address</div>
                        <div className='text-one'>205 Av. Viger O</div>
                        <div className='text-two'>Montréal, QC H2Z 1G2</div>
                    </div>
                    <div className='phone details'>
                        <i className='fas fa-phone-alt' />
                        <div className='topic'>Phone</div>
                        <div className='text-one'>(514) 316-0220</div>
                    </div>
                    <div className='email details'>
                        <i className='fas fa-envelope' />
                        <div className='topic'>Email</div>
                        <div className='text-one'>TheSacTeamBoutique@outlook.com</div>
                    </div>
                </div>
                <div className='right-side'>
                    <div className='topic-text'>Contact US</div>
                    <p className='topic-msg'>
                        If you have a message or an inquiry, please feel free to contact us.
                    </p>
                    <form onSubmit={handleSubmit}>
                        <div className='input-box'>
                            <input
                                type='text'
                                placeholder='Enter your name'
                                name='name'
                                value={formData.name}
                                onChange={handleChange}
                            />
                        </div>
                        <div className='input-box'>
                            <input
                                type='email'
                                placeholder='Enter your email'
                                name='email'
                                value={formData.email}
                                onChange={handleChange}
                            />
                        </div>
                        <div className='input-box message-box'>
                            <input
                                type='text'
                                placeholder='Enter your message'
                                name='message'
                                value={formData.message}
                                onChange={handleChange}
                            />
                        </div>
                        <div className='button'>
                            <input type='submit' value='Send Now' />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default ContactForm
