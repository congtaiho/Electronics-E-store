import React, { useState, useEffect } from 'react'
import {
    MDBBtn,
    MDBCard,
    MDBCardBody,
    MDBCardImage,
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBTextArea,
    MDBIcon,
    MDBTypography
} from 'mdb-react-ui-kit'
import axios from 'axios'
import '../css/commentStar.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faStar } from '@fortawesome/free-solid-svg-icons'
import { format } from 'date-fns'

function Comment ({ product }) {
    const productID = product.id
    console.log('productid', productID)
    const userLogin = localStorage.getItem('session')
    const objetUserLogin = JSON.parse(userLogin)
    const [comments, setComments] = useState([])
    const [addCommentRating, setAddCommentRating] = useState(0) // Đánh giá cho phần "Add a comment"
    const [commentText, setCommentText] = useState('')
    const [hoveredStar, setHoveredStar] = useState(0)
    // const [clickedStar, setClickedStar] = useState(0) // Đánh giá đã click

    // Hàm để lấy danh sách bình luận từ máy chủ
    const fetchComments = async () => {
        try {
            const response = await axios.get(`http://localhost:5000/comments/${productID}`)
            setComments(response.data)
        } catch (error) {
            console.error('Error:', error)
        }
    }

    // Hàm xử lý khi người dùng bấm nút "Clear"
    const clearComment = () => {
        setCommentText('') // Đặt giá trị của nội dung thành chuỗi rỗng
    }

    // Hàm để thêm bình luận mới
    const addComment = async () => {
        if (addCommentRating === 0) {
            alert('Please select a rating before adding a comment.')
            return
        }

        const newComment = {
            userId: objetUserLogin.id, // Sử dụng userId thực tế của người dùng
            rating: addCommentRating,
            text: commentText,
            createdAt: new Date().toISOString(),
            productId: productID
        }

        try {
            const response = await axios.post('http://localhost:5000/add-comment', newComment)

            if (response.status === 200) {
                fetchComments() // Sau khi thêm bình luận thành công, cập nhật danh sách bình luận
                setAddCommentRating(0) // Đặt lại đánh giá sao và nội dung bình luận
                setCommentText('')
            } else {
                console.error('Failed to add comment')
            }
        } catch (error) {
            console.error('Error:', error)
        }
    }

    // Lấy danh sách bình luận khi trang được tải lần đầu

    useEffect(
        () => {
            if (productID) {
                fetchComments(productID)
            }
        }
        , [productID])

    // Xử lý khi người dùng di chuột qua một ngôi sao
    const handleStarHover = (star) => {
        setHoveredStar(star)
    }

    // Xử lý khi người dùng rời chuột khỏi ngôi sao
    const handleStarLeave = () => {
        setHoveredStar(0)
    }

    // Xử lý khi người dùng click vào ngôi sao
    const handleStarClick = (star) => {
        setAddCommentRating(star)
        // setClickedStar(star)
    }

    // Hàm để hiển thị các ngôi sao và thay đổi màu sắc khi người dùng hover qua
    const renderRatingStars = () => {
        return [1, 2, 3, 4, 5].map((star) => (
            <span
                key={star}
                onMouseEnter={() => handleStarHover(star)}
                onMouseLeave={handleStarLeave}
                onClick={() => handleStarClick(star)}
                style={{
                    cursor: 'pointer',
                    color: star <= (hoveredStar || addCommentRating) ? 'gold' : 'gray'
                }}
            >
                <FontAwesomeIcon icon={faStar} className='star-icon' />
                {/* {star <= (hoveredStar || addCommentRating) ? '★' : '☆'} */}
            </span>
        ))
    }

    // afficher le commentaire

    console.log('user login', objetUserLogin)
    const renderComments = () => {
        return comments.map((comment, index) => (
            <MDBCardBody className='p-4' key={index}>
                <div className='d-flex align-items-center'>
                    <MDBCardImage
                        className='rounded-circle shadow-1-strong me-3'
                        src={`/public/images/${objetUserLogin.imageProfil}`}
                        alt='avatar'
                        width='60'
                        height='60'
                    />
                    <div>
                        <div className='d-flex align-items-center mb-2'>
                            <span className='me-2'>Rating:</span>
                            {[1, 2, 3, 4, 5].map((star) => (
                                <FontAwesomeIcon
                                    key={star}
                                    icon={faStar}
                                    className={`star-icon ${star <= comment.rating ? 'text-warning' : 'text-secondary'}`}
                                />
                            ))}
                        </div>
                        <p>{comment.text}</p>
                        <small className='text-muted'>{format(new Date(comment.createdAt), 'dd/MM/yyyy')}</small>
                    </div>
                </div>
                <hr className='my-0' />
            </MDBCardBody>
        ))
    }

    return (
        <section>
            <section className='row gx-5'>
                <MDBContainer className='py-5' style={{ maxWidth: '1400px' }}>
                    <MDBRow className='justify-content-center'>
                        <MDBCol md='4' lg='10'>
                            <MDBCard>
                                <MDBCardBody className='p-4'>
                                    <div className='d-flex flex-start w-100'>
                                        <MDBCardImage
                                            className='rounded-circle shadow-1-strong me-3'
                                            src={`/public/images/${objetUserLogin.imageProfil}`}
                                            alt='avatar'
                                            width='60'
                                            height='60'
                                        />
                                        <div className='w-100'>
                                            <MDBTypography tag='h5'>Add a comment</MDBTypography>
                                            <div>
                                                {renderRatingStars()}
                                                <MDBTextArea
                                                    rows={4}
                                                    value={commentText}
                                                    onChange={(e) => setCommentText(e.target.value)}
                                                    placeholder='Write your comment here...'
                                                />
                                            </div>
                                            <div className='d-flex justify-content-between mt-3'>
                                                <MDBBtn color='danger' onClick={clearComment}>Clear</MDBBtn>
                                                <MDBBtn color='success' onClick={addComment}>
                                                    Submit <MDBIcon fas icon='long-arrow-alt-right ms-1' />
                                                </MDBBtn>
                                            </div>
                                        </div>
                                    </div>
                                </MDBCardBody>
                            </MDBCard>
                        </MDBCol>
                    </MDBRow>
                </MDBContainer>
            </section>
            <MDBContainer className='py-5' style={{ maxWidth: '1400px' }}>
                <MDBRow className='justify-content-center'>
                    <MDBCol md='12' lg='10'>
                        <MDBCard className='text-dark'>
                            <MDBCardBody className='p-4'>
                                <MDBTypography tag='h4' className='mb-0'>
                                    Recent comments
                                </MDBTypography>
                                <p className='fw-light mb-4 pb-2'>Latest Comments section by users</p>
                            </MDBCardBody>
                            {renderComments()}

                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>
        </section>
    )
}

export default Comment
