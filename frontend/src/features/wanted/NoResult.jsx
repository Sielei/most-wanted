import {Search, XCircle} from "lucide-react";

const NoResult = ({ hasActiveFilters, onClearFilters }) => (
    <div className="col-span-full flex flex-col items-center justify-center py-12 px-4">
        <div className="bg-gray-50 rounded-lg p-8 max-w-md w-full flex flex-col items-center">
            <Search className="w-12 h-12 text-gray-400 mb-4" />
            <h3 className="text-lg font-medium text-gray-900 mb-2">
                No results found
            </h3>
            {hasActiveFilters ? (
                <>
                    <p className="text-gray-500 text-center mb-6">
                        No wanted persons match your current filter criteria. Try adjusting your filters or clear them to see all results.
                    </p>
                    <button
                        onClick={onClearFilters}
                        className="flex items-center gap-2 px-4 py-2 text-sm bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
                    >
                        <XCircle className="w-4 h-4" />
                        Clear All Filters
                    </button>
                </>
            ) : (
                <p className="text-gray-500 text-center">
                    There are currently no wanted persons in the database.
                </p>
            )}
        </div>
    </div>
);

export default NoResult;