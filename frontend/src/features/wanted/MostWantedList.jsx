import MostWantedCard from "@/features/wanted/components/MostWantedCard.jsx";
import { useEffect, useState } from "react";
import { setError } from "@/redux/authSlice.js";
import { ChevronLeft, ChevronRight } from "lucide-react";
import MostWantedFilters from "@/features/wanted/components/MostWantedFilters.jsx";

const MostWantedList = () => {
    const [mostWantedPersons, setMostWantedPersons] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalItems, setTotalItems] = useState(0);
    const [pageSize, setPageSize] = useState(25);
    const [isLoading, setIsLoading] = useState(false);
    const [activeFilters, setActiveFilters] = useState({});

    const BASE_URL = import.meta.env.VITE_BACKEND_URL;

    useEffect(() => {
        const fetchMostWantedPersons = async () => {
            setIsLoading(true);
            try {
                // Convert filters to URL parameters
                const filterParams = new URLSearchParams({
                    page: currentPage.toString(),
                    pageSize: pageSize.toString(),
                    ...Object.fromEntries(
                        Object.entries(activeFilters).filter(([_, value]) => value !== '')
                    )
                });

                const response = await fetch(
                    `${BASE_URL}/api/wanted?${filterParams.toString()}`,
                    { method: "GET" }
                );

                if (!response.ok) {
                    throw new Error("unable to fetch data");
                }

                const responseData = await response.json();
                setMostWantedPersons(responseData.data);
                setTotalItems(responseData.totalElements);
            }
            catch (error) {
                setError(error.message);
            }
            finally {
                setIsLoading(false);
            }
        }
        fetchMostWantedPersons();
    }, [currentPage, pageSize, activeFilters]);

    const totalPages = Math.ceil(totalItems / pageSize);
    const startItem = (currentPage - 1) * pageSize + 1;
    const endItem = Math.min(currentPage * pageSize, totalItems);

    const handlePageChange = (newPage) => {
        if (newPage >= 1 && newPage <= totalPages) {
            setCurrentPage(newPage);
        }
    };

    const handlePageSizeChange = (newSize) => {
        setPageSize(Number(newSize));
        setCurrentPage(1);
    };

    const handleFilterChange = (filters) => {
        setActiveFilters(filters);
        setCurrentPage(1); // Reset to first page when filters change
    };

    return (
        <div className="flex flex-col gap-6">
            <MostWantedFilters onFilterChange={handleFilterChange} />

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-6">
                {isLoading ? (
                    <div className="col-span-full text-center">Loading...</div>
                ) : (
                    mostWantedPersons.map((mostWantedPerson) => (
                        <MostWantedCard
                            key={mostWantedPerson.uid}
                            wantedPerson={mostWantedPerson}
                        />
                    ))
                )}
            </div>

            <div className="flex justify-between items-center px-6 pb-6">
                <div className="flex items-center gap-2">
                    <select
                        value={pageSize}
                        onChange={(e) => handlePageSizeChange(e.target.value)}
                        className="border rounded-md px-2 py-1 text-sm bg-white"
                    >
                        <option value={10}>10 per page</option>
                        <option value={25}>25 per page</option>
                        <option value={50}>50 per page</option>
                    </select>
                </div>

                <div className="flex items-center gap-4">
                    <span className="text-sm text-gray-600">
                        {startItem}-{endItem} of {totalItems}
                    </span>

                    <div className="flex items-center">
                        <button
                            onClick={() => handlePageChange(currentPage - 1)}
                            disabled={currentPage === 1 || isLoading}
                            className="p-1 rounded-md text-gray-600 hover:bg-gray-100 disabled:text-gray-300 disabled:hover:bg-transparent"
                        >
                            <ChevronLeft className="w-5 h-5" />
                        </button>

                        <button
                            onClick={() => handlePageChange(currentPage + 1)}
                            disabled={currentPage === totalPages || isLoading}
                            className="p-1 rounded-md text-gray-600 hover:bg-gray-100 disabled:text-gray-300 disabled:hover:bg-transparent"
                        >
                            <ChevronRight className="w-5 h-5" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MostWantedList;