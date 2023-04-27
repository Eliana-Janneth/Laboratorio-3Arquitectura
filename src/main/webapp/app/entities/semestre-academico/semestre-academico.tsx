import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISemestreAcademico } from 'app/shared/model/semestre-academico.model';
import { getEntities } from './semestre-academico.reducer';

export const SemestreAcademico = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const semestreAcademicoList = useAppSelector(state => state.semestreAcademico.entities);
  const loading = useAppSelector(state => state.semestreAcademico.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="semestre-academico-heading" data-cy="SemestreAcademicoHeading">
        <Translate contentKey="labArquiJHipsterApp.semestreAcademico.home.title">Semestre Academicos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="labArquiJHipsterApp.semestreAcademico.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/semestre-academico/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="labArquiJHipsterApp.semestreAcademico.home.createLabel">Create new Semestre Academico</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {semestreAcademicoList && semestreAcademicoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.semestreAcademico.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.semestreAcademico.semestreAcademicoId">Semestre Academico Id</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.semestreAcademico.codigo">Codigo</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.semestreAcademico.estado">Estado</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.semestreAcademico.anio">Anio</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.semestreAcademico.periodoAcademico">Periodo Academico</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {semestreAcademicoList.map((semestreAcademico, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/semestre-academico/${semestreAcademico.id}`} color="link" size="sm">
                      {semestreAcademico.id}
                    </Button>
                  </td>
                  <td>{semestreAcademico.semestreAcademicoId}</td>
                  <td>{semestreAcademico.codigo}</td>
                  <td>{semestreAcademico.estado ? 'true' : 'false'}</td>
                  <td>{semestreAcademico.anio}</td>
                  <td>{semestreAcademico.periodoAcademico}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/semestre-academico/${semestreAcademico.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/semestre-academico/${semestreAcademico.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/semestre-academico/${semestreAcademico.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="labArquiJHipsterApp.semestreAcademico.home.notFound">No Semestre Academicos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SemestreAcademico;
