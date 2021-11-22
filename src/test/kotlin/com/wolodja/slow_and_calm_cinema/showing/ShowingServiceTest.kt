package com.wolodja.slow_and_calm_cinema.showing

import com.wolodja.slow_and_calm_cinema.SampleDataProvider
import com.wolodja.slow_and_calm_cinema.movie.MovieProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.data.repository.findByIdOrNull
import java.util.*

internal class ShowingServiceTest {

    private val showingRepository: ShowingRepository = mockk()
    private val movieProvider: MovieProvider = mockk()
    private val showingService = ShowingService(showingRepository, movieProvider)

    @Test
    fun getActualShowings() {

        //given
        val movieId = UUID.randomUUID()
        every { movieProvider.getMovieEntity(movieId) } returns SampleDataProvider.movie
        every { showingRepository.getByMovieAndTimeIsAfter(SampleDataProvider.movie, any()) } returns emptyList()

        //when
        showingService.getActualShowings(movieId)

        //then
        verify(exactly = 1) { movieProvider.getMovieEntity(movieId) }
        verify(exactly = 1) { showingRepository.getByMovieAndTimeIsAfter(SampleDataProvider.movie, any()) }
    }

    @Test
    fun `should save valid showing`() {

        //given
        every { movieProvider.getMovieEntity(any()) } returns SampleDataProvider.movie
        every { showingRepository.save(any()) } returns SampleDataProvider.showing

        //when
        showingService.saveShowing(SampleDataProvider.showingDto)

        //then
        verify(exactly = 1) { movieProvider.getMovieEntity(any()) }
        verify(exactly = 1) { showingRepository.save(any()) }
    }

    @Test
    fun `should not save showing with invalid time`(){

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            showingService.saveShowing(SampleDataProvider.showingDtoWithInvalidTime)
        }
        //then
        verify(exactly = 0) { movieProvider.getMovieEntity(any()) }
        verify(exactly = 0) { showingRepository.save(any()) }
        assert(exception.message == "Showing time can't be in past.")
    }

    @Test
    fun `should not save showing with minimal price bigger then maximal price`(){

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            showingService.saveShowing(SampleDataProvider.showingDtoWithInvalidPrices)
        }
        //then
        verify(exactly = 0) { movieProvider.getMovieEntity(any()) }
        verify(exactly = 0) { showingRepository.save(any()) }
        assert(exception.message == "Invalid price provided.")
    }

    @Test
    fun `should not save showing with negative price value`(){

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            showingService.saveShowing(SampleDataProvider.showingDtoWithNegativePrice)
        }
        //then
        verify(exactly = 0) { movieProvider.getMovieEntity(any()) }
        verify(exactly = 0) { showingRepository.save(any()) }
        assert(exception.message == "Invalid price provided.")
    }

    @Test
    fun `should update valid showing`() {

        //given
        every { showingRepository.findByIdOrNull(any()) } returns SampleDataProvider.showing
        every { showingRepository.save(any()) } returns SampleDataProvider.showing

        //when
        showingService.updateShowing(UUID.randomUUID(), SampleDataProvider.showingDto)

        //then
        verify(exactly = 1) { showingRepository.findByIdOrNull(any()) }
        verify(exactly = 1) { showingRepository.save(any()) }
    }

    @Test
    fun `should not update showing with invalid time`(){

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            showingService.updateShowing(UUID.randomUUID(), SampleDataProvider.showingDtoWithInvalidTime)
        }
        //then
        verify(exactly = 0) { showingRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { showingRepository.save(any()) }
        assert(exception.message == "Showing time can't be in past.")
    }

    @Test
    fun `should not update showing with minimal price bigger then maximal price`(){

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            showingService.updateShowing(UUID.randomUUID(), SampleDataProvider.showingDtoWithInvalidPrices)
        }
        //then
        verify(exactly = 0) { showingRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { showingRepository.save(any()) }
        assert(exception.message == "Invalid price provided.")
    }

    @Test
    fun `should not update showing with negative price value`(){

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            showingService.updateShowing(UUID.randomUUID(), SampleDataProvider.showingDtoWithNegativePrice)
        }
        //then
        verify(exactly = 0) { showingRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { showingRepository.save(any()) }
        assert(exception.message == "Invalid price provided.")
    }

    @Test
    fun `should throw error when updating showing does not exists`(){
        //given
        every { showingRepository.findByIdOrNull(any()) } returns null

        //when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            showingService.updateShowing(UUID.randomUUID(), SampleDataProvider.showingDto)
        }
        //then
        verify(exactly = 1) { showingRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { showingRepository.save(any()) }
        assert(exception.message == "Provided showing does not exist.")
    }
}