package smartcast.uz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartcast.uz.domain.Transaction;
import smartcast.uz.enums.Currency;
import smartcast.uz.enums.TransactionType;

import java.util.UUID;
@Repository
public interface TransactionRepository extends BaseRepository<Transaction, UUID> {

    @Query("""
        select tr from Transaction tr 
            where tr.card.id = :cardId
                and (:type is null or tr.type = :type)
                and (:transactionId is null or tr.id = :transactionId)
                and (:externalId is null or tr.externalId = :externalId)
                and (:currency is null or tr.currency = :currency)
                and tr.deleted = false
        order by tr.createdDate asc
    """)
    Page<Transaction> getTransactionsByFilter(
            UUID cardId, TransactionType type, UUID transactionId, UUID externalId, Currency currency, Pageable pageable
    );
}
