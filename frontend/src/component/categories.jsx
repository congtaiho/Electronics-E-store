import React from 'react'
import { Link } from 'react-router-dom'

const Categories = ({ onSearchCategoryName }) => {
    const categories = [

        { id: 1, name: 'Appareil Photo' },
        { id: 2, name: 'Télévison' },
        { id: 3, name: 'Drone' },
        { id: 4, name: 'Musique Électronique' },
        { id: 5, name: 'Camera' },
        { id: 6, name: 'Jeux Video' },
        { id: 7, name: 'Telephones cellulaires' },
        { id: 8, name: 'Ordinateurs' },
        { id: 9, name: 'Tablettes' }
    ]

    const handleClickCategorie = (categoryId) => {
        onSearchCategoryName(categoryId)
    }

    return (
        <div className='categories btn-group'>
            <button
                type='button'
                className='btn btn-warning'
                data-toggle='dropdown'
                aria-haspopup='true'
            >
                Catégories
            </button>
            <ul className='dropdown-menu'>
                {categories.map((category) => (
                    <li key={category.id}>
                        <Link
                            className='dropdown-item'
                            to='/products/categorie'
                            onClick={() => handleClickCategorie(category.id)}
                        >
                            {category.name}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default Categories
