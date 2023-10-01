import React, { useState, useEffect } from 'react'
import Comment from './comment'
import { Link } from 'react-router-dom'

const Details = ({ selectedDetailProduct, onloadStateFromLocalStorage, onSaveStateToLocalStorage }) => {
    const [product, setProduct] = useState([])
    console.log('product trong trang details: ', product)
    const [quantity, setQuantity] = useState(1)
    const maxQuantity = product.quantity

    const sessionUser = onloadStateFromLocalStorage()

    const handleIncrease = () => {
        if (quantity < maxQuantity) {
            setQuantity(quantity + 1)
        }
    }

    const handleDecrease = () => {
        if (quantity > 1) {
            setQuantity(quantity - 1)
        }
    }

    const handleClickAddToCart = (product) => {
        if (sessionUser) {
            const existingItem = sessionUser.panier.articles.find(item => item.product.id === product.id)
            if (existingItem) {
                existingItem.quantity += quantity
            } else {
                sessionUser.panier.articles.push({ product, quantity })
            }
            onSaveStateToLocalStorage(sessionUser)
        }
    }

    useEffect(() => {
        if (selectedDetailProduct) {
            localStorage.setItem('selectedDetailProduct', JSON.stringify(selectedDetailProduct))
        }
        const storedProduct = localStorage.getItem('selectedDetailProduct')
        if (storedProduct) {
            const parsedProduct = JSON.parse(storedProduct)
            setProduct(parsedProduct)
        } else {
            setProduct(selectedDetailProduct)
        }
    }, [selectedDetailProduct])
    return (
        <>

            <div className='bg-primary'>
                <div className='container py-4'>
                    <nav className='d-flex'>
                        <h6 className='mb-0'>
                            <a href='' className='text-white-50'>Home</a>
                            <span className='text-white-50 mx-2'> {'>'} </span>
                            <a href='' className='text-white-50'>Library</a>
                            <span className='text-white-50 mx-2'> {'>'} </span>
                            <a href='' className='text-white'><u>Data</u></a>
                        </h6>
                    </nav>

                </div>
            </div>

            <section className='py-5'>
                <div className='container'>
                    <div className='row gx-5'>
                        <aside className='col-lg-6'>
                            <div className='border rounded-4 mb-3 d-flex justify-content-center'>
                                <a data-fslightbox='mygalley' className='rounded-4' target='_blank' data-type='image' href='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big.webp' rel='noreferrer'>
                                    <img style={{ maxWidth: '100%', maxHeight: '100vh', margin: 'auto' }} className='rounded-4 fit' src={product.image_url} />
                                </a>
                            </div>
                            <div className='d-flex justify-content-center mb-3'>
                                <a data-fslightbox='mygalley' className='border mx-1 rounded-2' target='_blank' data-type='image' href='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big1.webp' rel='noreferrer'>
                                    <img width='60' height='60' className='rounded-2' src='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big1.webp' />
                                </a>
                                <a data-fslightbox='mygalley' className='border mx-1 rounded-2' target='_blank' data-type='image' href='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big2.webp' rel='noreferrer'>
                                    <img width='60' height='60' className='rounded-2' src='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big2.webp' />
                                </a>
                                <a data-fslightbox='mygalley' className='border mx-1 rounded-2' target='_blank' data-type='image' href='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big3.webp' rel='noreferrer'>
                                    <img width='60' height='60' className='rounded-2' src='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big3.webp' />
                                </a>
                                <a data-fslightbox='mygalley' className='border mx-1 rounded-2' target='_blank' data-type='image' href='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big4.webp' rel='noreferrer'>
                                    <img width='60' height='60' className='rounded-2' src='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big4.webp' />
                                </a>
                                <a data-fslightbox='mygalley' className='border mx-1 rounded-2' target='_blank' data-type='image' href='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big.webp' rel='noreferrer'>
                                    <img width='60' height='60' className='rounded-2' src='https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/detail1/big.webp' />
                                </a>
                            </div>

                        </aside>
                        <main className='col-lg-6'>
                            <div className='ps-lg-3'>
                                <h4 className='title text-dark'>
                                    {product.nom} <br />
                                </h4>
                                <div className='d-flex flex-row my-3'>
                                    <div className='text-warning mb-1 me-2'>
                                        ★★★★☆ <span className='ms-1'>4.5</span>
                                    </div>
                                    <span className='text-muted'>✓ 154 orders</span>
                                    <span className='text-success ms-2' style={{ marginLeft: '.5rem' }}> In stock</span>
                                </div>

                                <div className='mb-3'>
                                    <span className='h5'>{'$' + product.prix}</span>
                                    <span className='text-muted'>/per box</span>
                                </div>

                                <p>
                                    {product.description}
                                </p>

                                <div className='row'>
                                    <dt className='col-3'>Categorie:</dt>
                                    <dd className='col-9'>{product.categorie}</dd>

                                    <dt className='col-3'>Brand</dt>
                                    <dd className='col-9'>{product.marque}</dd>
                                </div>

                                <hr />

                                <div className='row mb-4'>

                                    <div className='col-md-4 col-6 mb-3'>
                                        <label className='mb-2 d-block'>Quantity</label>
                                        <div className='input-group mb-3' style={{ width: '170px' }}>
                                            <button className='btn btn-white border border-secondary px-3' type='button' id='button-addon1' data-mdb-ripple-color='dark' onClick={handleDecrease}>-</button>
                                            <input type='text' className='form-control text-center border border-secondary' placeholder='1' aria-label='Example text with button addon' aria-describedby='button-addon1' value={quantity} />
                                            <button className='btn btn-white border border-secondary px-3' type='button' id='button-addon2' data-mdb-ripple-color='dark' onClick={handleIncrease}>+</button>
                                        </div>
                                    </div>
                                </div>
                                <Link to='/checkout' className='btn btn-warning shadow-0'> Buy now </Link>
                                <button onClick={() => handleClickAddToCart(product)} className='btn btn-primary shadow-0' style={{ marginLeft: '.5rem' }}> <i className='me-1 fa fa-shopping-basket' /> Add to cart </button>
                                <a href='#' className='btn btn-light border border-secondary py-2 icon-hover px-3' style={{ marginLeft: '.5rem' }}> <i className='me-1 fa fa-heart fa-lg' /> Save </a>
                            </div>
                        </main>
                    </div>
                </div>
            </section>
            {/* <!-- content --> */}
            <Comment product={product} />
            {/* <!-- end about section --> */}
        </>
    )
}

export default Details
