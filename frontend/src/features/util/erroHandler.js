import {toast} from "react-toastify";

export const handleApiError = (error) => {
    const errorMessage = error.response?.data?.message || error.message ||
        'An unexpected error occurred';

    toast.error(errorMessage, {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
    });
};