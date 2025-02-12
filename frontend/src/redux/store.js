import authReducer from '@/redux/authSlice';
import {configureStore} from "@reduxjs/toolkit";

export const store = configureStore({
    reducer: {
        auth: authReducer
    }
});