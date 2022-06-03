package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizacaoTopicoRequest
import br.com.alura.forum.dto.NovoTopicoRequest
import br.com.alura.forum.dto.TopicoPorCategoriaResponse
import br.com.alura.forum.dto.TopicoResponse
import br.com.alura.forum.exception.ErrorResponse
import br.com.alura.forum.model.Topico
import br.com.alura.forum.service.TopicoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearerAuth")
class TopicoController(private val service: TopicoService) {

    @Operation(
        summary = "Obtem uma lista de topicos",
        description = "Retorna uma lista de todos os topicos ou uma lista de topicos por nome do curso"
//        responses = [
//           ApiResponse(
//               responseCode = "200",
//               description = "Sucesso na busca de topicos",
//               content = [Content(schema = Schema(implementation = ErrorResponse::class))]
//           ),
//            ApiResponse(
//                responseCode = "500",
//                description = "Erro interno no serviço",
////                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
//            ),
//        ]

    )
//    @ApiResponses(value = [
//        ApiResponse(
//            responseCode = "200",
//            description = "Sucesso na busca de topicos",
//            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
//        ),
//        ApiResponse(
//            responseCode = "500",
//            description = "Erro interno no serviço",
////                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
//        )
//    ])

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Found Foos", content = [
            (Content(mediaType = "application/json", array = (
                    ArraySchema(schema = Schema(implementation = TopicoResponse::class)))))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Did not find any Foos", content = [Content()])]
    )
    @GetMapping
    @Cacheable("topicos")
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(
            size = 5,
            page = 0,
            sort = ["dataCriacao"],
            direction = Sort.Direction.DESC) paginacao: Pageable
    ): Page<TopicoResponse> {
        return service.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoResponse {
        return service.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrar(
        @RequestBody @Valid request: NovoTopicoRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoResponse> {
        val topicoView = service.cadastrar(request)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizar(@RequestBody @Valid request: AtualizacaoTopicoRequest): ResponseEntity<TopicoResponse> {
        val topicoView = service.atualizar(request)
        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun deletar(@PathVariable id: Long) {
        service.deletar(id)
    }

    @GetMapping("/relatorio")
    fun relatorio(): List<TopicoPorCategoriaResponse> {
        return service.relatorio()
    }
}