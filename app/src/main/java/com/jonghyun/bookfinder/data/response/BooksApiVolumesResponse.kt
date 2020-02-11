package com.jonghyun.bookfinder.data.response

data class BooksApiVolumesResponse(
    var kind: String,
    var totalItems: Int,
    var items: List<Volume>?
) {
    data class Volume(
        var kind: String,
        var id: String,
        var etag: String,
        var selfLink: String,
        var volumeInfo: VolumeInfo
    ) {
        data class VolumeInfo(
            var title: String,
            var subtitle: String,
            var authors: List<String>?,
            var publisher: String,
            var publishedDate: String,
            var description: String,
            var industryIdentifiers: List<IndustryIdentifier>,
            var pageCount: Int,
            var dimensions: Dimension,
            var printType: String,
            var mainCategory: String,
            var categories: List<String>,
            var averageRating: Double,
            var ratingsCount: Int,
            var contentVersion: String,
            var imageLinks: ImageLink?,
            var language: String,
            var previewLink: String,
            var infoLink: String,
            var canonicalVolumeLink: String

        ) {
            data class IndustryIdentifier(
                var type: String,
                var identifier: String
            )

            data class Dimension(
                var height: String,
                var width: String,
                var thickness: String
            )

            data class ImageLink(
                var smallThumbnail: String,
                var thumbnail: String,
                var small: String,
                var medium: String,
                var large: String,
                var extraLarge: String
            )
        }

        // data class userInfo() null
        data class saleInfo(var country: String)
        data class accessInfo(var country: String)
        data class searchInfo(var textSnippet: String)
    }
}