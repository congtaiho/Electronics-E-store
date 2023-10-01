import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Categories from '../component/categories'
import ConnectButton from './connect_button'
import Utilisateur from '../../../backend/entities/Utilisateur'

const Header = ({ onSearchCategoryName, onSearchQueryChange, handleReloadProduct, onloadStateFromLocalStorage, onSaveStateToLocalStorage }) => {
    const [searchQuery, setSearchQuery] = useState('')
    const navigate = useNavigate()
    let sessionUser = onloadStateFromLocalStorage()
    if (!sessionUser) {
        sessionUser = new Utilisateur('', '', '', '', '', '', '', '', '', 'icon_account.png')
        onSaveStateToLocalStorage(sessionUser)
    }

    const handleSearch = (event) => {
        const newSearchQuery = event.target.value
        setSearchQuery(newSearchQuery)
        onSearchQueryChange(newSearchQuery)
    }
    const handleSubmit = (event) => {
        event.preventDefault()
        handleReloadProduct()
        navigate('/products')
    }

    return (
        <div>

            <header className='header_section'>
                <div className='header_top'>
                    <div className='container-fluid'>
                        <div className='top_nav_container'>
                            <div className='contact_nav '>
                                <Link className='nav-link' to='/home'>
                                    <h1>The Sac Team Boutique</h1>
                                </Link>

                            </div>
                            <form onSubmit={handleSubmit} className='search_form'>
                                <input
                                    type='search'
                                    className='form-control'
                                    placeholder='Search here...'
                                    value={searchQuery}
                                    onChange={handleSearch}
                                />
                                <button className='search-button' type='submit' />
                            </form>
                            <div className='user_option_box'>

                                {

                                    sessionUser.session === false
                                        ? (
                                            <>
                                                <Link className='nav-link' to='/account'>
                                                    <ConnectButton handleReloadProduct={handleReloadProduct} onloadStateFromLocalStorage={onloadStateFromLocalStorage} onSaveStateToLocalStorage={onSaveStateToLocalStorage} />
                                                </Link>

                                            </>
                                        )
                                        : (

                                            <div className='nav-link'>
                                                <ConnectButton handleReloadProduct={handleReloadProduct} onloadStateFromLocalStorage={onloadStateFromLocalStorage} onSaveStateToLocalStorage={onSaveStateToLocalStorage} />
                                            </div>

                                        )
                                }

                                <Link className='nav-link' to='/checkout'>
                                    <img className='icon' src='/public/images/icon_cart.png' alt='' />
                                    <span>Cart</span>
                                </Link>
                            </div>
                        </div>

                    </div>
                </div>
                <div className='header_bottom'>
                    <div className='container-fluid'>
                        <nav className='navbar navbar-expand-lg custom_nav-container '>
                            <div className='navbar-brand'>
                                <span><Categories onSearchCategoryName={onSearchCategoryName} /> </span>
                            </div>

                            <button
                                className='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarSupportedContent'
                                aria-controls='navbarSupportedContent' aria-expanded='false' aria-label='Toggle navigation'
                            >
                                <span className=''> </span>
                            </button>

                            <div className='collapse navbar-collapse' id='navbarSupportedContent'>
                                <ul className='navbar-nav '>
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
                                    <li className='nav-item'>
                                        <Link className='nav-link' to='/contact'>Contact US</Link>
                                    </li>
                                </ul>
                            </div>
                        </nav>
                    </div>
                </div>
            </header>

        </div>
    )
}
export default Header
