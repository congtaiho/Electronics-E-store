import React from 'react'
import { Link } from 'react-router-dom'

const Footer = () => {
    return (
        <div>

            <section className='info_section '>
                <div className='container'>
                    <div className='row'>

                        <div className='col-md-3'>
                            <div className='info_info'>
                                <h5>
                                    Information
                                </h5>
                                <p>
                                    Eligendi sunt, provident, debitis nemo, facilis cupiditate velit libero dolorum aperiam enim nulla iste
                                    maxime corrupti ad illo libero minus.
                                </p>
                            </div>
                        </div>
                        <div className='col-md-3'>
                            <div className='info_links'>
                                <h5>
                                    Useful Link
                                </h5>
                                <ul>
                                    <li className='nav-item'>
                                        <Link className='nav-link' to='/home'>Home</Link>
                                    </li>
                                    <li className='nav-item'>
                                        <Link className='nav-link' to='/products'>Products</Link>
                                    </li>
                                    <li className='nav-item'>
                                        <Link className='nav-link' to='/about'>About</Link>
                                    </li>
                                    <li className='nav-item'>
                                        <Link className='nav-link' to='/whyus'>Why Us</Link>
                                    </li>
                                    <li className='nav-item'>
                                        <Link className='nav-link' to='/testimony'>Testimonial</Link>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div className='col-md-3'>
                            <div className='info_form '>
                                <h5>
                                    Newsletter
                                </h5>
                                <form action=''>
                                    <input type='email' placeholder='Enter your email' />
                                    <button>
                                        <Link className='nav-link' to='/account'> Subscribe</Link>
                                    </button>
                                </form>
                                <div className='social_box'>
                                    <Link to='https://facebook.com/' />
                                    <Link to='https://twitter.com/' />
                                    <Link to='https://www.instagram.com/' />
                                    <Link to='https://www.youtube.com/' />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <footer className='footer_section'>
                <div className='container'>
                    <div className='copyright'>
                        <span id='displayYear'>  &copy; All Rights Reserved By</span>
                        <Link to='https://github.com/EchoCodeInk/W17_projet_final'>Evan Cholette |</Link>
                        <Link to='https://github.com/EchoCodeInk/W17_projet_final'>Taoufik Boussemousse |</Link>
                        <Link to='https://github.com/EchoCodeInk/W17_projet_final'>Sara Salek |</Link>
                        <Link to='https://github.com/EchoCodeInk/W17_projet_final'>Antoine Ouelette |</Link>
                        <Link to='https://github.com/EchoCodeInk/W17_projet_final'>Charles-Maximilien Gros |</Link>
                        <Link to='https://github.com/EchoCodeInk/W17_projet_final'>Công Tai Hô</Link>

                    </div>
                </div>
            </footer>

        </div>
    )
}
export default Footer
