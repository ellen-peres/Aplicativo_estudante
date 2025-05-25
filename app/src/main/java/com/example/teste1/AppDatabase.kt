import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teste1.MODEL.Avaliacao
import com.example.teste1.DAO.AvaliacaoDao

@Database(entities = [Avaliacao::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun avaliacaoDao(): AvaliacaoDao
}
