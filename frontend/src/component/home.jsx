import React from 'react'
import { Link } from 'react-router-dom'

const Home = () => {
    return (

        <div className='hero_area'>

            <section className='slider_section '>
                <div id='customCarousel1' className='carousel slide' data-ride='carousel'>
                    <div className='carousel-inner'>
                        <div className='carousel-item active'>
                            <div className='container '>
                                <div className='row'>
                                    <div className='col-md-6'>
                                        <div className='detail-box'>
                                            <h1>
                                                Welcome to our shop
                                            </h1>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iste quam velit saepe dolorem deserunt
                                                quo quidem ad optio.
                                            </p>
                                            <Link className='nav-link' to='/products'> Shop now</Link>
                                        </div>
                                    </div>
                                    <div className='col-md-6'>
                                        <div className='img-box'>
                                            <img src='/public/images/slider-img.png' alt='Slider' />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className='carousel-item'>
                            <div className='container '>
                                <div className='row'>
                                    <div className='col-md-6'>
                                        <div className='detail-box'>
                                            <h1>
                                                Welcome to our shop
                                            </h1>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iste quam velit saepe dolorem deserunt
                                                quo quidem ad optio.
                                            </p>
                                            <Link className='nav-link' to='/products'> Shop now</Link>
                                        </div>
                                    </div>
                                    <div className='col-md-6'>
                                        <div className='img-box'>
                                            <img src='/public/images/p3.png' alt='' />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className='carousel-item'>
                            <div className='container '>
                                <div className='row'>
                                    <div className='col-md-6'>
                                        <div className='detail-box'>
                                            <h1>
                                                Welcome to our shop
                                            </h1>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iste quam velit saepe dolorem deserunt
                                                quo quidem ad optio.
                                            </p>
                                            <Link className='nav-link' to='/products'> Shop now</Link>

                                        </div>
                                    </div>
                                    <div className='col-md-6'>
                                        <div className='img-box'>
                                            <img src='/public/images/p2.png' alt='' />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='carousel_btn_box'>
                        <a className='carousel-control-prev' href='#customCarousel1' role='button' data-slide='prev'>
                            <i className='fa fa-angle-left' aria-hidden='true' />
                            <span className='sr-only'>Previous</span>
                        </a>
                        <a className='carousel-control-next' href='#customCarousel1' role='button' data-slide='next'>
                            <i className='fa fa-angle-right' aria-hidden='true' />
                            <span className='sr-only'>Next</span>
                        </a>
                    </div>
                </div>
            </section>

        </div>

    )
}
export default Home
