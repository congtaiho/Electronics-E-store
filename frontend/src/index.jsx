import React, { useState } from 'react'
import { createRoot } from 'react-dom/client'
import { Routes, Route, BrowserRouter, useNavigate } from 'react-router-dom'
import './css/bootstrap.css'
import './css/style.css'
import './css/ion.rangeSlider.min.css'
import './css/responsive.css'
import './css/style.scss'

import Header from './component/header'
import Footer from './component/footer'
import Home from './component/home'
import Products from './component/products'
import About from './component/about'
import Whyus from './component/whyus'
import Testimony from './component/testimony'
import Account from './component/account'
import Register from './component/register'
import Details from './component/details'
import Checkout from './component/check_out'
import Delivery from './component/delivery'
import ProfileManager from './component/profilManager'
import OrderConfirmation from './component/order_confirm'
import ContactForm from './component/contact'
import ContactConfirmation from './component/contactSupportConfirm'

const container = document.getElementById('root')

const root = createRoot(container)

function App () {
    const [selectedDetailProduct, setSelectedDetailProduct] = useState()
    const [categoryName, setCategoryName] = useState()
    const [searchQueryFromHeader, setSearchQueryFromHeader] = useState('')
    const [reloadKey, setReloadKey] = useState(0)
    const navigate = useNavigate()

    const handleReloadProduct = () => {
        setReloadKey(reloadKey + 1)
    }

    const saveStateToLocalStorage = (sessionUser) => {
        localStorage.setItem('session', JSON.stringify(sessionUser))
    }

    const loadStateFromLocalStorage = () => {
        const storedState = localStorage.getItem('session')
        return storedState ? JSON.parse(storedState) : null
    }

    const handleSearchQueryChange = (searchQuery) => {
        setSearchQueryFromHeader(searchQuery)
        console.log('searchQueryFromHeader', searchQueryFromHeader)
    }
    const handleSearchCategoryName = (categoryName) => {
        setCategoryName(categoryName)
        console.log('searchQueryFromHeader', searchQueryFromHeader)
    }
    const handleSelectedDetailProduct = (productSelected) => {
        console.log('handleSelectedDetailProduct', productSelected)
        setSelectedDetailProduct(productSelected)
        navigate('/details')
    }

    return (
        <div>
            <Header onSearchCategoryName={handleSearchCategoryName} onSearchQueryChange={handleSearchQueryChange} handleReloadProduct={handleReloadProduct} onloadStateFromLocalStorage={loadStateFromLocalStorage} onSaveStateToLocalStorage={saveStateToLocalStorage} />
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/home' element={<Home />} />
                <Route path='/products' element={<Products onSelectedDetailProduct={handleSelectedDetailProduct} key={reloadKey} searchQuery={searchQueryFromHeader} />} />
                <Route path='/about' element={<About />} />
                <Route path='/whyus' element={<Whyus />} />
                <Route path='/testimony' element={<Testimony />} />
                <Route path='/contact' element={<ContactForm />} />
                <Route path='/account' element={<Account onloadStateFromLocalStorage={loadStateFromLocalStorage} onSaveStateToLocalStorage={saveStateToLocalStorage} />} />
                <Route path='/register' element={<Register />} />
                <Route path='/details' element={<Details selectedDetailProduct={selectedDetailProduct} onloadStateFromLocalStorage={loadStateFromLocalStorage} onSaveStateToLocalStorage={saveStateToLocalStorage} />} />
                <Route path='/products/categorie' element={<Products onSelectedDetailProduct={handleSelectedDetailProduct} searchCategorieName={categoryName} />} />
                <Route path='/checkout' element={<Checkout key={reloadKey} onloadStateFromLocalStorage={loadStateFromLocalStorage} onSaveStateToLocalStorage={saveStateToLocalStorage} />} />
                <Route path='/delivery' element={<Delivery onloadStateFromLocalStorage={loadStateFromLocalStorage} onSaveStateToLocalStorage={saveStateToLocalStorage} />} />
                <Route path='/profil_manager' element={<ProfileManager onloadStateFromLocalStorage={loadStateFromLocalStorage} onSaveStateToLocalStorage={saveStateToLocalStorage} />} />
                <Route path='/orderConfirmation' element={<OrderConfirmation />} />
                <Route path='/contactSupportConfirm' element={<ContactConfirmation />} />
            </Routes>
            <Footer />
        </div>
    )
}

root.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>
)
