import React, { useState } from 'react'
import { MDBAccordion, MDBAccordionItem, MDBCard, MDBCardBody, MDBCardHeader, MDBCheckbox, MDBCol, MDBContainer, MDBInput, MDBRow, MDBTypography } from 'mdb-react-ui-kit'
import { useNavigate } from 'react-router'
import axios from 'axios'
import { Link } from 'react-router-dom'
import sweetalert from 'sweetalert2'
function Delivery ({ onloadStateFromLocalStorage, onSaveStateToLocalStorage }) {
    const sessionUser = onloadStateFromLocalStorage()

    const navigate = useNavigate()

    const [firstNAme, setFirstNAme] = useState(sessionUser.prenom)
    const [lastName, setLastName] = useState(sessionUser.nom)
    const [userEmail, setUserEmail] = useState(sessionUser.email)
    const [noCivique, setNoCivique] = useState(sessionUser.noCivique)
    const [street, setStreet] = useState(sessionUser.street)
    const [city, setCity] = useState(sessionUser.city)
    const [pays, setPays] = useState(sessionUser.pays)
    const paniers = sessionUser.panier.articles

    console.log('firstNAme', firstNAme)
    console.log('userEmail', userEmail)

    const informationsAchat = paniers.map(item => {
        return `Nom de l'item : ${item.product.nom}
         Quantity : ${item.quantity}
                `
    })

    const contenuEmail = informationsAchat.join('')

    function envoyerEmailAchat (contenuEmail) {
        const email = userEmail
        const sujet = 'Confirmation d\'achat'

        axios.post('http://localhost:5000/envoyer-email', {
            destinataire: email,
            sujet,
            contenu: contenuEmail
        })
            .then(response => {
                if (response.status === 200) {
                    sessionUser.panier.articles = []
                    onSaveStateToLocalStorage(sessionUser)
                    navigate('/orderConfirmation')
                } else {
                    console.error('Erreur lors de l\'envoi de l\'e-mail')
                    sweetalert.fire({
                        title: 'Erreur lors de l\'envoi de l\'e-mail'
                    })
                }
            })
            .catch(error => {
                console.error('Erreur lors de l\'envoi de l\'e-mail', error)
                sweetalert.fire({
                    title: 'Erreur lors de l\'envoi de l\'e-mail'
                })
            })
    }

    return (
        <MDBContainer className='my-5 py-5' style={{ maxWidth: '1100px' }}>
            <section>
                <MDBRow>
                    <MDBCol md='12'>

                        <MDBAccordion className='card mb-4'>
                            <MDBAccordionItem collapseId={1} className='border-0' headerTitle='Promo/Student Code or Vouchers'>
                                <MDBInput label='Enter code' type='text' />
                            </MDBAccordionItem>
                        </MDBAccordion>

                    </MDBCol>

                    <MDBCol md='12' className='mb-4'>
                        <MDBCard className='mb-4'>
                            <MDBCardHeader className='py-3'>
                                <MDBTypography tag='h5' className='mb-0 text-font text-uppercase'>Delivery address</MDBTypography>
                            </MDBCardHeader>
                            <MDBCardBody>
                                <form>
                                    <MDBRow className='mb-4'>
                                        <MDBCol>
                                            <MDBInput label='First name' type='text' value={firstNAme || null} onChange={(e) => setFirstNAme(e.target.value)} />
                                        </MDBCol>
                                        <MDBCol>
                                            <MDBInput label='Last name' type='text' value={lastName || null} onChange={(e) => setLastName(e.target.value)} />
                                        </MDBCol>
                                    </MDBRow>
                                    <MDBInput label='Email' type='text' className='mb-4' value={userEmail || null} onChange={(e) => setUserEmail(e.target.value)} />
                                    <MDBRow className='mb-4'>

                                        <MDBCol>
                                            <MDBInput label='No civique' type='text' value={noCivique || null} onChange={(e) => setNoCivique(e.target.value)} />
                                        </MDBCol>
                                        <MDBCol>
                                            <MDBInput label='Street' type='text' value={street || null} onChange={(e) => setStreet(e.target.value)} />
                                        </MDBCol>
                                        <MDBCol>
                                            <MDBInput label='City' type='text' value={city || null} onChange={(e) => setCity(e.target.value)} />
                                        </MDBCol>
                                        <MDBCol>
                                            <MDBInput label='Pays' type='text' value={pays || null} onChange={(e) => setPays(e.target.value)} />
                                        </MDBCol>
                                    </MDBRow>

                                    {sessionUser.session === false
                                        ? (
                                            <div className='d-flex justify-content-center'>
                                                <MDBCheckbox name='flexCheck' value='' id='flexCheckChecked' label='Create an account?' defaultChecked />
                                            </div>
                                        )
                                        : null}
                                </form>
                            </MDBCardBody>
                        </MDBCard>

                        <div className='text-center'>
                            <Link className='btn btn-warning button-order col-md-10' onClick={() => envoyerEmailAchat(contenuEmail)}>Place order</Link>
                        </div>

                    </MDBCol>
                </MDBRow>
            </section>
        </MDBContainer>
    )
}

export default Delivery
