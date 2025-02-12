import { useState } from 'react';
import { X } from 'lucide-react';

const MostWantedFilters = ({ onFilterChange }) => {
    // State for current filter values (not yet applied)
    const [filters, setFilters] = useState({
        sex: '',
        hairColor: '',
        eyeColor: '',
        race: '',
        name: '',
        fieldOffice: '',
        status: ''
    });

    // State for applied filter values (what's actually being used)
    const [appliedFilters, setAppliedFilters] = useState({
        sex: '',
        hairColor: '',
        eyeColor: '',
        race: '',
        name: '',
        fieldOffice: '',
        status: ''
    });

    const [showFilters, setShowFilters] = useState(false);

    // Predefined options for select fields
    const options = {
        sex: ['Male', 'Female'],
        hairColor: ['Black', 'Brown', 'Blonde', 'Red', 'Gray', 'White'],
        eyeColor: ['Brown', 'Blue', 'Green', 'Hazel', 'Black'],
        race: ['White', 'Black', 'Asian', 'Hispanic', 'Native American', 'Other'],
        status: ['Wanted', 'Located', 'Captured']
    };

    const handleInputChange = (field, value) => {
        setFilters(prev => ({
            ...prev,
            [field]: value
        }));
    };

    const handleApplyFilters = () => {
        setAppliedFilters(filters);
        onFilterChange(filters);
    };

    const clearFilters = () => {
        const emptyFilters = Object.keys(filters).reduce((acc, key) => {
            acc[key] = '';
            return acc;
        }, {});
        setFilters(emptyFilters);
        setAppliedFilters(emptyFilters);
        onFilterChange(emptyFilters);
    };

    // Check if current filters are different from applied filters
    const hasChanges = JSON.stringify(filters) !== JSON.stringify(appliedFilters);

    return (
        <div className="mb-6">
            <div className="flex items-center gap-4 mb-4">
                <button
                    onClick={() => setShowFilters(!showFilters)}
                    className="px-4 py-2 text-sm bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
                >
                    {showFilters ? 'Hide Filters' : 'Show Filters'}
                </button>

                {showFilters && (
                    <button
                        onClick={clearFilters}
                        className="px-4 py-2 text-sm bg-gray-100 text-gray-600 rounded-md hover:bg-gray-200 transition-colors flex items-center gap-2"
                    >
                        <X className="w-4 h-4" />
                        Clear Filters
                    </button>
                )}
            </div>

            {showFilters && (
                <div className="flex flex-col gap-4">
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4 p-4 bg-gray-50 rounded-lg">
                        <div className="flex flex-col gap-1">
                            <label className="text-sm text-gray-600">Name</label>
                            <input
                                type="text"
                                value={filters.name}
                                onChange={(e) => handleInputChange('name', e.target.value)}
                                placeholder="Search by name"
                                className="px-3 py-2 border rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        </div>

                        <div className="flex flex-col gap-1">
                            <label className="text-sm text-gray-600">Field Office</label>
                            <input
                                type="text"
                                value={filters.fieldOffice}
                                onChange={(e) => handleInputChange('fieldOffice', e.target.value)}
                                placeholder="Enter field office"
                                className="px-3 py-2 border rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        </div>

                        {Object.entries(options).map(([field, values]) => (
                            <div key={field} className="flex flex-col gap-1">
                                <label className="text-sm text-gray-600 capitalize">
                                    {field.replace(/([A-Z])/g, ' $1').trim()}
                                </label>
                                <select
                                    value={filters[field]}
                                    onChange={(e) => handleInputChange(field, e.target.value)}
                                    className="px-3 py-2 border rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                                >
                                    <option value="">All</option>
                                    {values.map((value) => (
                                        <option key={value} value={value}>
                                            {value}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        ))}
                    </div>

                    <div className="flex justify-end">
                        <button
                            onClick={handleApplyFilters}
                            disabled={!hasChanges}
                            className={`px-4 py-2 rounded-md text-white transition-colors ${
                                hasChanges
                                    ? 'bg-blue-500 hover:bg-blue-600'
                                    : 'bg-gray-300 cursor-not-allowed'
                            }`}
                        >
                            Apply Filters
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default MostWantedFilters;