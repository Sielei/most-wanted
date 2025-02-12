import {useLocation, useParams} from 'react-router-dom';

const WantedPersonDetail = () => {
    const { id } = useParams();
    const location = useLocation();
    const wantedPerson = location.state?.wantedPerson;

    const isWanted = wantedPerson.caution ? true : false;
    const isSeekingInfo = wantedPerson.caution ? false :
        wantedPerson.subjects.includes("Seeking Information") ? true : false;

    if (!wantedPerson) {
        return <div className="p-8 text-center">Loading...</div>;
    }

    return (
        <div className="max-w-4xl mx-auto p-6 space-y-8">
            {/* Header Section */}
            <div className="space-y-4">
                <div className={`inline-block px-4 py-1 text-white font-semibold rounded-full ${
                    wantedPerson.caution ? 'bg-red-600' : 'bg-blue-600'
                }`}>
                    {wantedPerson.caution ? 'WANTED BY THE FBI' :
                        wantedPerson.subjects.includes("Seeking Information") ? 'SEEKING INFORMATION' :
                            'MISSING PERSON'}
                </div>
                <h1 className="text-4xl font-bold">{wantedPerson.title}</h1>
                {wantedPerson.warning_message && (
                    <div className="bg-red-100 border-l-4 border-red-500 p-4">
                        <p className="text-red-700">{wantedPerson.warning_message}</p>
                    </div>
                )}
            </div>

            {/* Images Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {wantedPerson.images.map((image, index) => (
                    <div key={index} className="relative">
                        <img
                            src={image.original}
                            alt={image.caption || `Image ${index + 1}`}
                            className="w-full rounded-lg object-cover"
                        />
                        {image.caption && (
                            <p className="mt-2 text-sm text-gray-600">{image.caption}</p>
                        )}
                    </div>
                ))}
            </div>

            {/* Details Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                {/* Personal Information */}
                <div className="space-y-6">
                    <h2 className="text-2xl font-semibold">Personal Information</h2>
                    <div className="grid grid-cols-2 gap-4">
                        {wantedPerson.dates_of_birth_used && (
                            <div>
                                <p className="text-gray-600 font-medium">Date of Birth</p>
                                <p>{wantedPerson.dates_of_birth_used.join(', ')}</p>
                            </div>
                        )}
                        {wantedPerson.place_of_birth && (
                            <div>
                                <p className="text-gray-600 font-medium">Place of Birth</p>
                                <p>{wantedPerson.place_of_birth}</p>
                            </div>
                        )}
                        {wantedPerson.hair && (
                            <div>
                                <p className="text-gray-600 font-medium">Hair Color</p>
                                <p>{wantedPerson.hair}</p>
                            </div>
                        )}
                        {wantedPerson.eyes && (
                            <div>
                                <p className="text-gray-600 font-medium">Eye Color</p>
                                <p>{wantedPerson.eyes}</p>
                            </div>
                        )}
                        {wantedPerson.height_max && (
                            <div>
                                <p className="text-gray-600 font-medium">Height</p>
                                <p>{wantedPerson.height_max}</p>
                            </div>
                        )}
                        {wantedPerson.weight && (
                            <div>
                                <p className="text-gray-600 font-medium">Weight</p>
                                <p>{wantedPerson.weight} lbs</p>
                            </div>
                        )}
                        {wantedPerson.sex && (
                            <div>
                                <p className="text-gray-600 font-medium">Sex</p>
                                <p>{wantedPerson.sex}</p>
                            </div>
                        )}
                        {wantedPerson.race && (
                            <div>
                                <p className="text-gray-600 font-medium">Race</p>
                                <p>{wantedPerson.race}</p>
                            </div>
                        )}
                        {wantedPerson.nationality && (
                            <div>
                                <p className="text-gray-600 font-medium">Nationality</p>
                                <p>{wantedPerson.nationality}</p>
                            </div>
                        )}
                    </div>
                </div>

                {/* Case Information */}
                <div className="space-y-6">
                    <h2 className="text-2xl font-semibold">Case Information</h2>
                    {wantedPerson.field_offices && (
                        <div>
                            <p className="text-gray-600 font-medium">Field Office</p>
                            <p>{wantedPerson.field_offices.join(', ')}</p>
                        </div>
                    )}
                    {wantedPerson.details && (
                        <div>
                            <p className="text-gray-600 font-medium">Details</p>
                            <div className="mt-2 prose"
                                 dangerouslySetInnerHTML={{ __html: wantedPerson.details }}
                            />
                        </div>
                    )}
                    {wantedPerson.description && (
                        <div>
                            <p className="text-gray-600 font-medium">
                                {isWanted ? 'Wanted For' : isSeekingInfo ? 'Location' :'Last Seen'}
                            </p>
                            <p className="mt-2">{wantedPerson.description}</p>
                        </div>
                    )}
                    {wantedPerson.caution && (
                        <div>
                            <p className="text-gray-600 font-medium">Caution</p>
                            <div className="mt-2 prose"
                                 dangerouslySetInnerHTML={{ __html: wantedPerson.caution }}
                            />
                        </div>
                    )}
                    {wantedPerson.remarks && (
                        <div>
                            <p className="text-gray-600 font-medium">Remarks</p>
                            <div className="mt-2 prose"
                                 dangerouslySetInnerHTML={{ __html: wantedPerson.remarks }}
                            />
                        </div>
                    )}
                </div>
            </div>

            {/* Additional Information */}
            <div className="space-y-6">
                {wantedPerson.reward_text && (
                    <div className="bg-green-50 border-l-4 border-green-500 p-4">
                        <p className="text-green-700 font-semibold">Reward</p>
                        <p className="mt-2">{wantedPerson.reward_text}</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default WantedPersonDetail;